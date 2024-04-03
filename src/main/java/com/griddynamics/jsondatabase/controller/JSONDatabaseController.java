package com.griddynamics.jsondatabase.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.griddynamics.jsondatabase.repository.JSONDatabaseModel;
import com.griddynamics.jsondatabase.repository.JSONFileManager;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Setter
public class JSONDatabaseController implements DatabaseController {
  private final JSONDatabaseModel jsonDatabaseModel;
  private final JSONFileManager jsonFileManager;
  private final ReentrantReadWriteLock.WriteLock writeLock;
  private final ReentrantReadWriteLock.ReadLock readLock;

  public JSONDatabaseController(JSONFileManager jsonFileManager) {
    this.jsonDatabaseModel = new JSONDatabaseModel();
    this.jsonFileManager = jsonFileManager;
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    this.writeLock = readWriteLock.writeLock();
    this.readLock = readWriteLock.readLock();
  }

  public void setData(JsonElement key, JsonElement value) {
    writeLock.lock();
    Optional<JsonObject> optionalDatabase = jsonFileManager.updateDatabase();
    if (Objects.nonNull(optionalDatabase)) {
      jsonDatabaseModel.setDatabase(optionalDatabase.orElseGet(JsonObject::new));
    } else {
      jsonDatabaseModel.setDatabase(new JsonObject());
    }
    try {
      if (key.isJsonPrimitive()) {
        jsonDatabaseModel.getDatabase().add(key.getAsString(), value);
      } else if (key.isJsonArray()) {
        JsonArray keys = key.getAsJsonArray();
        String toAdd = keys.remove(keys.size() - 1).getAsString();
        findElement(keys.asList(), true).getAsJsonObject().add(toAdd, value);
      }
    } finally {
      jsonFileManager.updateJSON(jsonDatabaseModel.getDatabase());
      writeLock.unlock();
    }
  }

  public JsonElement getData(JsonElement key) {
    readLock.lock();
    jsonDatabaseModel.setDatabase(
        jsonFileManager.updateDatabase().orElse(new JSONDatabaseModel().getDatabase()));
    try {
      return (key.isJsonPrimitive() && jsonDatabaseModel.getDatabase().has(key.getAsString()))
          ? jsonDatabaseModel.getDatabase().get(key.getAsString())
          : key.isJsonArray() ? findElement(key.getAsJsonArray().asList(), false) : null;
    } finally {
      readLock.unlock();
    }
  }

  public String deleteData(JsonElement key) {
    writeLock.lock();
    jsonDatabaseModel.setDatabase(
        jsonFileManager.updateDatabase().orElse(new JSONDatabaseModel().getDatabase()));
    try {
      if (key.isJsonPrimitive() && jsonDatabaseModel.getDatabase().has(key.getAsString())) {
        jsonDatabaseModel.getDatabase().remove(key.getAsString());
        return OutputMessages.OK;
      } else if (key.isJsonArray()) {
        JsonArray keys = key.getAsJsonArray();
        String toDelete = keys.remove(keys.size() - 1).getAsString();
        findElement(keys.asList(), true).getAsJsonObject().remove(toDelete);
        return OutputMessages.OK;
      } else {
        return OutputMessages.ERROR;
      }
    } finally {
      jsonFileManager.updateDatabase();
      jsonFileManager.updateJSON(jsonDatabaseModel.getDatabase());
      writeLock.unlock();
    }
  }

  private JsonElement findElement(List<JsonElement> keys, boolean createIfAbsent) {
    JsonElement tmp = jsonDatabaseModel.getDatabase();
    for (JsonElement key : keys) {
      JsonObject obj = tmp.getAsJsonObject();
      String keyStr = key.getAsString();
      if (createIfAbsent && !obj.has(keyStr)) {
        obj.add(keyStr, new JsonObject());
      }
      tmp = obj.has(keyStr) ? obj.get(keyStr) : null;
      if (tmp == null) {
        break;
      }
    }
    return tmp;
  }
}
