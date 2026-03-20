package com.example.myapplication.api;

import java.util.List;

public interface VolleyCallback<T> {
    void onSuccess(List<T> result);
}
