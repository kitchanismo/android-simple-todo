package com.example.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIService {
    private String url = "https://zt5gf41p-3000.asse.devtunnels.ms/";
    private RequestQueue queue;

    public APIService(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getNames(VolleyCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Person> list = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.optJSONObject(i);
                        if (obj != null) {
                            String id = obj.optString("id");           // get id from JSON
                            String name = obj.optString("name");       // get name from JSON
                            list.add(new Person(name, id));            // create Person
                        }
                    }
                    callback.onSuccess(list);
                },
                error -> error.printStackTrace()
        );

        queue.add(request);
    }

}
