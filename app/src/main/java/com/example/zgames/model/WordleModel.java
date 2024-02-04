package com.example.zgames.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zgames.ServiceClient;
import com.example.zgames.tools.JsonConverter;
import com.example.zgames.types.WordlePlayer;

import org.json.JSONObject;

public class WordleModel {
    public interface GetPlayerResponseHandler {
        void response(WordlePlayer player);
        void error();
    }

    public interface GetWordResponseHandler {
        void response(String word);
        void error();
    }

    public interface AdvanceLevelResponseHandler {
        void response();
        void error();
    }

    private final String BASE_URL = "http://192.168.0.72:8000/zgames/wordle";

    public void getPlayer(Context ctx, int userId, GetPlayerResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, String.format("%s/get_player/%d", BASE_URL, userId), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                WordlePlayer player = JsonConverter.deserialize(response, "data", WordlePlayer.class);
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

    public void getWord(Context ctx, int wordleId, GetWordResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, String.format("%s/get_word/%d", BASE_URL, wordleId), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String word = JsonConverter.deserialize(response, "data", String.class);
                handler.response(word);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.error();
            }
        });
        ServiceClient.sendRequest(ctx, jsonObjectRequest);
    }

    public void advanceLevel(Context ctx, int wordleId, AdvanceLevelResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, String.format("%s/advance/%d", BASE_URL, wordleId), null, new Response.Listener<JSONObject>() {
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
    }
}
