package com.griddynamics.jsondatabase.controller;

import com.google.gson.JsonPrimitive;
import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import com.griddynamics.jsondatabase.server.response.ErrorResponse;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.response.ValueResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {
  private JSONDatabaseController mockDatabase;
  private Controller controller;

  @BeforeEach
  void init() {
    mockDatabase = mock(JSONDatabaseController.class);
    controller = new Controller(mockDatabase);
  }

  @Test
  void shouldSetDataSuccessfully() {
    // given
    Request request = new Request("set", new JsonPrimitive("key"), new JsonPrimitive("value"));
    // when
    Response response = controller.set(request);
    // then
    verify(mockDatabase).setData(request.getKey(), request.getValue());
    assertEquals(OutputMessages.OK, response.getResponse());
  }

  @Test
  void shouldReturnDataWhenKeyExists() {
    // given
    Request request = new Request("get", new JsonPrimitive("key"));
    // when
    when(mockDatabase.getData(any())).thenReturn(new JsonPrimitive("value"));
    Controller controller = new Controller(mockDatabase);
    Response response = controller.get(request);
    // then
    assertInstanceOf(ValueResponse.class, response);
    assertEquals(OutputMessages.OK, response.getResponse());
    assertEquals("value", ((ValueResponse) response).getValue().getAsString());
  }

  @Test
  void shouldReturnErrorWhenKeyDoesNotExist() {
    when(mockDatabase.getData(any())).thenReturn(null);

    Controller controller = new Controller(mockDatabase);
    Request request = new Request("get", new JsonPrimitive("key"));

    Response response = controller.get(request);

    assertInstanceOf(ErrorResponse.class, response);
    assertEquals(OutputMessages.ERROR, response.getResponse());
    assertEquals(OutputMessages.NO_SUCH_KEY, ((ErrorResponse) response).getReason());
  }

  @Test
  void shouldDeleteDataSuccessfully() {
    when(mockDatabase.deleteData(any())).thenReturn(OutputMessages.OK);

    Controller controller = new Controller(mockDatabase);
    Request request = new Request("delete", new JsonPrimitive("key"));

    Response response = controller.delete(request);

    verify(mockDatabase).deleteData(request.getKey());
    assertEquals(OutputMessages.OK, response.getResponse());
  }

  @Test
  void shouldReturnErrorWhenDeletingNonExistingKey() {
    when(mockDatabase.deleteData(any())).thenReturn(OutputMessages.ERROR);

    Controller controller = new Controller(mockDatabase);
    Request request = new Request("delete", new JsonPrimitive("key"));

    Response response = controller.delete(request);

    assertInstanceOf(ErrorResponse.class, response);
    assertEquals(OutputMessages.ERROR, response.getResponse());
    assertEquals(OutputMessages.NO_SUCH_KEY, ((ErrorResponse) response).getReason());
  }
}
