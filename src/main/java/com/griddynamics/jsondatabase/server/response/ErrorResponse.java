package com.griddynamics.jsondatabase.server.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse extends Response {
  private final String reason;

  public ErrorResponse(String response, String reason) {
    super(response);
    this.reason = reason;
  }
}
