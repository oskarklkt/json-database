package com.griddynamics.jsondatabase.client.input;

import com.beust.jcommander.Parameter;

public class ClientArgs {
    @Parameter(names = {"--type", "-t"}, description = "Type of request (set, get, delete, exit)", required = true)
    private String type;

    @Parameter(names = {"--key", "-k"}, description = "Key of the record in JSON database")
    private String key;

    @Parameter(names = {"--value", "-v"}, description = "Value to set in the cell")
    private String value;

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
