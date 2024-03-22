package com.griddynamics.jsondatabase.controller;

import com.griddynamics.jsondatabase.client.request.Request;
import com.google.gson.JsonElement;
import com.griddynamics.jsondatabase.repository.JSONDatabaseModel;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import com.griddynamics.jsondatabase.server.response.ErrorResponse;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.response.ValueResponse;

public class JSONDatabaseController {

  public JSONDatabaseController(JSONDatabaseModel database) {
    this.database = database;
  }

  private final JSONDatabaseModel database;

  public Response set(Request request) {
    database.setData(request.getKey(), request.getValue());
    return new Response(OutputMessages.OK);
  }

  public Response get(Request request) {
    JsonElement data = database.getData(request.getKey());
    if (data == null) {
      return new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    } else {
      return new ValueResponse(OutputMessages.OK, data);
    }
  }

  public Response delete(Request request) {
    String data = database.deleteData(request.getKey());
    if (data.equals(OutputMessages.ERROR)) {
      return new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY);
    } else {
      return new Response(OutputMessages.OK);
    }
  }
}