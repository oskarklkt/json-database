package com.griddynamics.jsondatabase.server.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Response {
  private final String response;

  public Response(String response) {
    this.response = response;
  }
}
