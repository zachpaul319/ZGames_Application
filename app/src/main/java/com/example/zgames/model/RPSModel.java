package com.example.zgames.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zgames.ServiceClient;
import com.example.zgames.tools.JsonConverter;
import com.example.zgames.types.RPSPlayer;

import org.json.JSONObject;

public class RPSModel {
    public interface GetPlayerResponseHandler {
        void response(RPSPlayer player);
        void error();
    }

    public interface UpdateStreakResponseHandler {
        void response();
        void error();
    }

    private final String BASE_URL = "http://192.168.0.72:8000/zgames/rps";

    public void getPlayer(Context ctx, int userId, GetPlayerResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, String.format("%s/get_player/%d", BASE_URL, userId), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                RPSPlayer player = JsonConverter.deserialize(response, "data", RPSPlayer.class);
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

    public void updateStreak(Context ctx, int rpsId, int longestStreak, UpdateStreakResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, String.format("%s/update_streak/%d?longestStreak=%d", BASE_URL, rpsId, longestStreak), null, new Response.Listener<JSONObject>() {
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
