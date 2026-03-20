package com.example.myapplication.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIService<T> {
    private String url = "https://zt5gf41p-3000.asse.devtunnels.ms/";
    private RequestQueue queue;

    public APIService(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getList(String path, JsonMapper<T> mapper, VolleyCallback<T> callback) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + path, null,
                response -> {
                    List<T> list = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.optJSONObject(i);
                        if (obj != null) {
                            list.add(mapper.map(obj));
                        }
                    }
                    callback.onSuccess(list);
                },
                error -> error.printStackTrace()
        );
        queue.add(request);
    }

    /**
     * Generic POST method
     * @param path The endpoint path
     * @param body The JSONObject to send
     * @param mapper Tells us how to map the response back to T
     * @param callback Returns the newly created item T
     */
    public void postItem(String path, JSONObject body, JsonMapper<T> mapper, SingleItemCallback<T> callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url + path, body,
                response -> {
                    if (callback != null) {
                        callback.onSuccess(mapper.map(response));
                    }
                },
                error -> error.printStackTrace()
        );
        queue.add(request);
    }

    /**
     * Generic PUT method for updates
     * @param path The endpoint path
     * @param body The JSONObject with updated data
     * @param mapper Tells us how to map the response back to T
     * @param callback Returns the updated item T
     */
    public void updateItem(String path, JSONObject body, JsonMapper<T> mapper, SingleItemCallback<T> callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url + path, body,
                response -> {
                    if (callback != null) {
                        callback.onSuccess(mapper.map(response));
                    }
                },
                error -> error.printStackTrace()
        );
        queue.add(request);
    }

    public interface SingleItemCallback<T> {
        void onSuccess(T result);
    }
}
