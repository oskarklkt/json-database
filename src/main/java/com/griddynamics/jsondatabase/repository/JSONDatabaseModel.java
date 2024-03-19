package com.griddynamics.jsondatabase.repository;

import com.griddynamics.jsondatabase.server.messages.OutputMessages;

import java.util.HashMap;
import java.util.Map;

public class JSONDatabaseModel {
    private final Map<String, String> database;
    public JSONDatabaseModel() {
        this.database = new HashMap<>();
    }
    public void setData(String key, String value) {
        database.put(key, value);
    }
    public String getData(String key) {
        return database.getOrDefault(key, OutputMessages.ERROR);

    }
    public String deleteData(String key) {
        if (database.containsKey(key)) {
            database.remove(key);
            return OutputMessages.OK;
        } else {
            return OutputMessages.ERROR;
        }
    }
}
