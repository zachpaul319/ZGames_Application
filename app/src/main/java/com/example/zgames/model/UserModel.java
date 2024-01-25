package com.example.zgames.model;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zgames.ServiceClient;
import com.example.zgames.tools.JsonConverter;
import com.example.zgames.types.AuthRequest;
import com.example.zgames.types.User;

import org.json.JSONObject;

public class UserModel {
    public interface GetUserResponseHandler {
        void response(User user);
        void error();
    }

    public interface CreateUserResponseHandler {
        void response(int status);
        void error();
    }

    private final String BASE_URL = "http://192.168.0.72:8000/zgames/users";

    public void getUserByAuth(Context ctx, String username, String password, GetUserResponseHandler handler) {
        JsonObjectRequest jsonObjectRequest = new AuthRequest(Request.Method.GET, String.format("%s/get_user", BASE_URL), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user = JsonConverter.deserialize(response, "data", User.class);
                handler.response(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.error();
            }
        });
        AuthRequest.username = username;
        AuthRequest.password = password;
        ServiceClient.sendRequest(ctx, jsonObjectRequest);
    }

    public void getUserById(Context ctx, int userId, GetUserResponseHandler handler) {
        @SuppressLint("DefaultLocale") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, String.format("%s/get_user/%d", BASE_URL, userId), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user = JsonConverter.deserialize(response, "data", User.class);
                handler.response(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.error();
            }
        });
        ServiceClient.sendRequest(ctx, jsonObjectRequest);
    }

    public void createUser(Context ctx, User user, CreateUserResponseHandler handler) {
        JSONObject jsonObject = JsonConverter.serialize(user);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, String.format("%s/create_user", BASE_URL), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int status = JsonConverter.deserialize(response, "status", Integer.class);
                handler.response(status);
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
