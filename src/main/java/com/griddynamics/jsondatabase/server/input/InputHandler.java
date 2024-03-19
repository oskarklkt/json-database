package com.griddynamics.jsondatabase.server.input;

import com.griddynamics.jsondatabase.client.request.Request;
import com.google.gson.Gson;

public class InputHandler {
    public Request handleInput(String input) {
        Gson gson = new Gson();
        return gson.fromJson(input, Request.class);
    }
}
