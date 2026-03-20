package com.example.myapplication.api;

import com.example.myapplication.models.Person;
import java.util.List;

public interface VolleyCallback {
    void onSuccess(List<Person> result);
}
