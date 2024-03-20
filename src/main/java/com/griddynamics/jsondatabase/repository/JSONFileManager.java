package com.griddynamics.jsondatabase.repository;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.griddynamics.jsondatabase.server.data.DataConstants;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class JSONFileManager {

    public Map<String, String> updateDatabase() {
        Gson gson = new Gson();
        Map<String, String> database = null;
        try (FileReader reader = new FileReader(DataConstants.PATH)) {
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            database = gson.fromJson(reader, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return database;
    }

    public void updateJSON(Map<String, String> database) {
        Gson gson = new GsonBuilder().create();
        try (FileWriter writer = new FileWriter(DataConstants.PATH)) {
            gson.toJson(database, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
