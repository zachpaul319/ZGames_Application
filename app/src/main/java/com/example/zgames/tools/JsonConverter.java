package com.example.zgames.tools;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class JsonConverter {
    public static JSONObject serialize(Object object) {
        String json = (new Gson()).toJson(object);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static <T> T deserialize(JSONObject response, String responseField, Class<T> classOfT) {
        T item = null;
        try {
            item = (new Gson()).fromJson(response.get(responseField).toString(), classOfT);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }

    public static <T> ArrayList<T> deserializeList(JSONObject response, String responseField, Class<T[]> classArrOfT) {
        T[] arr = null;
        try {
            arr = (new Gson()).fromJson(response.get(responseField).toString(), classArrOfT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert arr != null;
        return new ArrayList<>(Arrays.asList(arr));
    }
}
