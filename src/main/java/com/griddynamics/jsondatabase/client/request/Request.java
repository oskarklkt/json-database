package com.griddynamics.jsondatabase.client.request;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Request {
  private final String type;
  private JsonElement key;
  private JsonElement value;

  public Request(String type) {
    this.type = type;
  }

  public Request(String type, JsonElement key) {
    this(type);
    this.key = key;
  }

  public Request(String type, JsonElement key, JsonElement value) {
    this(type, key);
    this.value = value;
  }

  public String parseJSON() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
