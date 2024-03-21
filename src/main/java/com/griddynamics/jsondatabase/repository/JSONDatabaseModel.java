package com.griddynamics.jsondatabase.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Setter
public class JSONDatabaseModel {
  private JsonObject database;
  private JSONFileManager jsonFileManager;
  private final ReentrantReadWriteLock.WriteLock writeLock;
  private final ReentrantReadWriteLock.ReadLock readLock;

  public JSONDatabaseModel() {
    this.database = new JsonObject();
    this.jsonFileManager = new JSONFileManager();
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    this.writeLock = readWriteLock.writeLock();
    this.readLock = readWriteLock.readLock();
  }

  public void setData(JsonElement key, JsonElement value) {
    writeLock.lock();
    database = jsonFileManager.updateDatabase();
    try {
      if (database == null) {
        database = new JsonObject();
        database.add(key.getAsString(), value);
      } else {
        if (key.isJsonPrimitive()) {
          database.add(key.getAsString(), value);
        } else if (key.isJsonArray()) {
          JsonArray keys = key.getAsJsonArray();
          String toAdd = keys.remove(keys.size() - 1).getAsString();
          findElement(keys, true).getAsJsonObject().add(toAdd, value);
        }
      }
    } finally {
      jsonFileManager.updateJSON(database);
      writeLock.unlock();
    }
  }

  public JsonElement getData(JsonElement key) {
    readLock.lock();
    database = jsonFileManager.updateDatabase();
    try {
      if (key.isJsonPrimitive() && database.has(key.getAsString())) {
        return database.get(key.getAsString());
      } else if (key.isJsonArray()) {
        return findElement(key.getAsJsonArray(), false);
      } else {
        return null;
      }
    } finally {
      readLock.unlock();
    }
  }

  public String deleteData(JsonElement key) {
    writeLock.lock();
    database = jsonFileManager.updateDatabase();
    try {
      if (key.isJsonPrimitive() && database.has(key.getAsString())) {
        database.remove(key.getAsString());
        return OutputMessages.OK;
      } else if (key.isJsonArray()) {
        JsonArray keys = key.getAsJsonArray();
        String toDelete = keys.remove(keys.size() - 1).getAsString();
        findElement(keys, true).getAsJsonObject().remove(toDelete);
        return OutputMessages.OK;
      } else {
        return OutputMessages.ERROR;
      }
    } finally {
      jsonFileManager.updateDatabase();
      jsonFileManager.updateJSON(database);
      writeLock.unlock();
    }
  }

  private JsonElement findElement(JsonArray keys, boolean createIfAbsent) {
    JsonElement tmp = database;
    if (createIfAbsent) {
      for (JsonElement key : keys) {
        if (!tmp.getAsJsonObject().has(key.getAsString())) {
          tmp.getAsJsonObject().add(key.getAsString(), new JsonObject());
        }
        tmp = tmp.getAsJsonObject().get(key.getAsString());
      }
    } else {
      for (JsonElement key : keys) {
        tmp = tmp.getAsJsonObject().get(key.getAsString());
      }
    }
    return tmp;
  }
}
