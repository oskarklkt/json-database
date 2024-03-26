package com.griddynamics.jsondatabase.server;

import com.google.gson.JsonPrimitive;
import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.controller.Controller;
import com.griddynamics.jsondatabase.server.messages.InputMessages;
import com.griddynamics.jsondatabase.server.socket.ServerConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ServerSideAppTest {

  private static Controller controller;
  private static ServerConnection serverConnection;

  @BeforeEach
  void setUp() {
    controller = mock(Controller.class);
    serverConnection = mock(ServerConnection.class);
  }

  @AfterAll
  static void close() {
    serverConnection.exit();
  }

  @Test
  void processCommandShouldCallGetOnGetCommand() {
    // Given
    Request getRequest = new Request(InputMessages.GET, new JsonPrimitive("1"));

    // When
    Controller temp = ServerSideApp.controller;
    ServerSideApp.controller = controller;
    ServerSideApp.processCommand(getRequest);

    // Then
    verify(controller).get(getRequest);
    ServerSideApp.controller = temp;
  }

  @Test
  void processCommandShouldCallSetOnSetCommand() {
    // Given
    Request setRequest =
        new Request(InputMessages.SET, new JsonPrimitive("1"), new JsonPrimitive("val"));

    // When
    Controller temp = ServerSideApp.controller;
    ServerSideApp.controller = controller;
    ServerSideApp.processCommand(setRequest);

    // Then
    verify(controller).set(setRequest);
    ServerSideApp.controller = temp;
  }

  @Test
  void processCommandShouldCallDeleteOnDeleteCommand() {
    // Given
    Request deleteRequest = new Request(InputMessages.DELETE, new JsonPrimitive("1"));

    // When
    Controller temp = ServerSideApp.controller;
    ServerSideApp.controller = controller;
    ServerSideApp.processCommand(deleteRequest);

    // Then
    verify(controller).delete(deleteRequest);
    ServerSideApp.controller = temp;
  }
}
