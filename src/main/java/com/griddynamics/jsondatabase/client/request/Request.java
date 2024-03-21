package com.griddynamics.jsondatabase.client.request;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;

import java.util.Objects;

@Getter
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Request request)) return false;
    return Objects.equals(type, request.type)
        && Objects.equals(key, request.key)
        && Objects.equals(value, request.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, key, value);
  }
}
