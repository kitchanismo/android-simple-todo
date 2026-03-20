package com.example.myapplication.api;

import org.json.JSONObject;

@FunctionalInterface
public interface JsonMapper<T> {
    T map(JSONObject jsonObject);
}