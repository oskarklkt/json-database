package com.griddynamics.jsondatabase.client.request;

import com.google.gson.Gson;

import java.util.Objects;

public class Request {
    private final String type;
    private String key;
    private String value;

    public Request(String type) {
        this.type = type;
    }

    public Request(String type, String key) {
        this(type);
        this.key = key;
    }

    public Request(String type, String key, String value) {
        this(type, key);
        this.value = value;
    }

    public String parseJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return Objects.equals(type, request.type) && Objects.equals(key, request.key) && Objects.equals(value, request.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key, value);
    }
}
