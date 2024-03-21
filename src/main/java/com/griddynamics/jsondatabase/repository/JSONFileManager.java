package com.griddynamics.jsondatabase.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.griddynamics.jsondatabase.server.data.DataConstants;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONFileManager {

  public JsonObject updateDatabase() {
    Gson gson = new Gson();
    JsonObject database = null;
    try (FileReader reader = new FileReader(DataConstants.PATH)) {
      database = gson.fromJson(reader, JsonObject.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return database;
  }

  public void updateJSON(JsonObject database) {
    Gson gson = new GsonBuilder().create();
    try (FileWriter writer = new FileWriter(DataConstants.PATH)) {
      gson.toJson(database, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
