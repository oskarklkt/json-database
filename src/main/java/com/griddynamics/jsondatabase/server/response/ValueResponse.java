package com.griddynamics.jsondatabase.server.response;

import com.google.gson.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ValueResponse extends Response {
  private final JsonElement value;

  public ValueResponse(String response, JsonElement value) {
    super(response);
    this.value = value;
  }
}
