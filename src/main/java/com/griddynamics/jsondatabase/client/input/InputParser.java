package com.griddynamics.jsondatabase.client.input;

import com.griddynamics.jsondatabase.client.data.DataConstants;
import com.griddynamics.jsondatabase.client.request.Request;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import lombok.SneakyThrows;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputParser {
  public static Request parseRequest(ClientArgs clientArgs) {
    String input = clientArgs.getInput();
    if (input != null) {
      return readRequestFromJsonFile(input);
    }
    String type = clientArgs.getType();
    String key = clientArgs.getKey();
    String value = clientArgs.getValue();
    if (value == null && key == null) {
      return new Request(type);
    } else if (value == null) {
      return new Request(type, new JsonPrimitive(key));
    } else {
      return new Request(type, new JsonPrimitive(key), new JsonPrimitive(value));
    }
  }

  @SneakyThrows
  public static Request readRequestFromJsonFile(String file) {
    Gson gson = new Gson();
    Request request;
    String json = Files.readString(Paths.get(DataConstants.PATH + file));
    request = gson.fromJson(json, Request.class);
    return request;
  }
}
