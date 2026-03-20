package com.example.myapplication.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
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

    public void getList(JsonMapper<T> mapper, VolleyCallback<T> callback) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<T> list = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.optJSONObject(i);
                        if (obj != null) {
                            // Use the provided mapper to create the object
                            list.add(mapper.map(obj));
                        }
                    }
                    callback.onSuccess(list);
                },
                error -> error.printStackTrace()
        );
        queue.add(request);
    }
}
