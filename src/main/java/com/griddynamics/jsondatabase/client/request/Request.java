package com.griddynamics.jsondatabase.client.request;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Request {
  private final String type;
  private final JsonElement key;
  private final JsonElement value;

  public Request(String type, JsonElement key) {
    this.type = type;
    this.key = key;
    this.value = null;
  }

  public Request(String type, JsonElement key, JsonElement value) {
    this.type = type;
    this.key = key;
    this.value = value;
  }

  public String parseJSON() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
