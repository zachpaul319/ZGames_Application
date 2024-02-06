package com.example.zgames.model;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zgames.ServiceClient;
import com.example.zgames.tools.JsonConverter;
import com.example.zgames.types.Sequence;
import com.example.zgames.types.SimonPlayer;

import org.json.JSONObject;

import java.util.List;

public class SimonModel extends BaseModel {
    public interface SequenceResponseHandler {
        void response(List<String> sequence);
        void error();
    }

    public interface GetPlayerResponseHandler {
        void response(SimonPlayer player);
        void error();
    }

    public interface UpdateScoreResponseHandler {
        void response();
        void error();
    }

    private final String ROUTED_URL = String.format("%s/zgames/simon", BASE_URL);

    private void setRetryPolicy(JsonObjectRequest jsonObjectRequest) {
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void startNewGame(Context ctx, SequenceResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, String.format("%s/new_game", ROUTED_URL), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<String> sequence = JsonConverter.deserializeList(response, "sequence", String[].class);
                handler.response(sequence);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.error();
            }
        });
        this.setRetryPolicy(jsonObjectRequest);
        ServiceClient.sendRequest(ctx, jsonObjectRequest);
    }

    public void sendSequence(Context ctx, Sequence sequence, SequenceResponseHandler handler) {
        JSONObject jsonObject = JsonConverter.serialize(sequence);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, String.format("%s/flash_sequence", ROUTED_URL), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<String> sequence = JsonConverter.deserializeList(response, "sequence", String[].class);
                handler.response(sequence);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.error();
            }
        });
        this.setRetryPolicy(jsonObjectRequest);
        ServiceClient.sendRequest(ctx, jsonObjectRequest);
    }

    public void getPlayer(Context ctx, int userId, GetPlayerResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, String.format("%s/get_player/%d", ROUTED_URL, userId), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SimonPlayer player = JsonConverter.deserialize(response, "data", SimonPlayer.class);
                handler.response(player);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.error();
            }
        });
        ServiceClient.sendRequest(ctx, jsonObjectRequest);
    }

    public void updateScore(Context ctx, int simonId, int highScore, UpdateScoreResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, String.format("%s/update_score/%d?highScore=%d", ROUTED_URL, simonId, highScore), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handler.response();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.error();
            }
        });
        ServiceClient.sendRequest(ctx, jsonObjectRequest);
    }
}
