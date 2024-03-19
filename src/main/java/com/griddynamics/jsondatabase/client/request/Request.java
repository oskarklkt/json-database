package com.griddynamics.jsondatabase.client.request;

import com.google.gson.Gson;

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
}
