package com.griddynamics.jsondatabase.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.griddynamics.jsondatabase.server.data.DataConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JSONFileManager {

  public Optional<JsonObject> updateDatabase() {
    Gson gson = new Gson();
    JsonObject database = null;
    try (FileReader reader = new FileReader(DataConstants.PATH)) {
      database = gson.fromJson(reader, JsonObject.class);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return Optional.ofNullable(database);
  }

  public void updateJSON(JsonObject database) {
    Gson gson = new GsonBuilder().create();
    try (FileWriter writer = new FileWriter(DataConstants.PATH)) {
      gson.toJson(database, writer);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
}
