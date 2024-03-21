package com.griddynamics.jsondatabase.server.response;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Response {
  private final String response;

  public Response(String response) {
    this.response = response;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Response response1)) return false;
    return Objects.equals(response, response1.response);
  }

  @Override
  public int hashCode() {
    return Objects.hash(response);
  }
}
