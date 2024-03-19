package com.griddynamics.jsondatabase.server.response;

public class ValueResponse extends Response {
    private final String value;
    public ValueResponse(String response, String value) {
        super(response);
        this.value = value;
    }
}
