package com.griddynamics.jsondatabase.server.input;

import com.google.gson.JsonPrimitive;
import com.griddynamics.jsondatabase.client.request.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

  @Test
  void handleInputGetTest() {

    // given
    InputHandler inputHandler = new InputHandler();
    String s = "{\"type\":\"get\",\"key\":\"Secret key\"}";
    // then
    assertEquals(new Request("get", new JsonPrimitive("Secret key")), inputHandler.handleInput(s));
  }

  @Test
  void handleInputSetTest() {
    // given
    String s = "{\"type\":\"set\",\"key\":\"Secret key\",\"value\":\"Secret value\"}";
    InputHandler inputHandler = new InputHandler();
    // then
    assertEquals(
        new Request("set", new JsonPrimitive("Secret key"), new JsonPrimitive("Secret value")),
        inputHandler.handleInput(s));
  }

  @Test
  void handleInputDeleteTest() {
    // given
    InputHandler inputHandler = new InputHandler();
    String s = "{\"type\":\"delete\",\"key\":\"Key\"}";
    // then
    assertEquals(new Request("delete", new JsonPrimitive("Key")), inputHandler.handleInput(s));
  }
}
