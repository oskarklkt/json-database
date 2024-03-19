package com.griddynamics.jsondatabase.client.input;

import com.griddynamics.jsondatabase.client.request.Request;

public class InputParser {
    public static Request parseRequest(ClientArgs clientArgs) {
        String type = clientArgs.getType();
        String key = clientArgs.getKey();
        String value = clientArgs.getValue();
        if (value == null && key == null) {
            return new Request(type);
        } else if (value == null){
            return new Request(type, key);
        } else {
            return new Request(type, key, value);
        }
    }
}
