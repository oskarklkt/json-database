package com.griddynamics.jsondatabase.controller;

import com.griddynamics.jsondatabase.client.request.Request;
import com.google.gson.JsonElement;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import com.griddynamics.jsondatabase.server.response.ErrorResponse;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.response.ValueResponse;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Controller {
  private final JSONDatabaseController controller;

  public Response set(Request request) {
    controller.setData(request.getKey(), request.getValue());
    return new Response(OutputMessages.OK);
  }

  public Response get(Request request) {
    JsonElement data = controller.getData(request.getKey());
    return Objects.isNull(data)
        ? new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY)
        : new ValueResponse(OutputMessages.OK, data);
  }

  public Response delete(Request request) {
    String data = controller.deleteData(request.getKey());
    return data.equals(OutputMessages.ERROR)
        ? new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY)
        : new Response(OutputMessages.OK);
  }
}
