package com.griddynamics.jsondatabase.repository;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JSONDatabaseModel {
  private JsonObject database;

  public JSONDatabaseModel() {
    this.database = new JsonObject();
  }
}
