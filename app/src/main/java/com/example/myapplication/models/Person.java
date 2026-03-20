package com.example.myapplication.models;

public class Person {
    private String name;
    private String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public String getId() {
        return this.id;
    }
}
