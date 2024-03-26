package com.griddynamics.jsondatabase.controller;

import com.google.gson.JsonElement;

public interface DatabaseController {
  String deleteData(JsonElement key);

  void setData(JsonElement key, JsonElement value);

  JsonElement getData(JsonElement key);
}
