package com.griddynamics.jsondatabase.client.input;

import com.google.gson.JsonPrimitive;
import com.griddynamics.jsondatabase.client.request.Request;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.nio.file.NoSuchFileException;
import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

  @Mock ClientArgs clientArgs = Mockito.mock(ClientArgs.class);

  @Test
  void parseRequestWithAllFieldsTest() {
    // when
    Mockito.when(clientArgs.getKey()).thenReturn("1");
    Mockito.when(clientArgs.getType()).thenReturn("set");
    Mockito.when(clientArgs.getValue()).thenReturn("test");
    Request testRequest = InputParser.parseRequest(clientArgs);
    // then
    assertEquals(new JsonPrimitive(clientArgs.getKey()), testRequest.getKey());
    assertEquals(clientArgs.getType(), testRequest.getType());
    assertEquals(new JsonPrimitive(clientArgs.getValue()), testRequest.getValue());
  }

  @Test
  void parseRequestWithoutValueTest() {
    // when
    Mockito.when(clientArgs.getKey()).thenReturn("1");
    Mockito.when(clientArgs.getType()).thenReturn("set");
    Mockito.when(clientArgs.getValue()).thenReturn(null);
    Request testRequest = InputParser.parseRequest(clientArgs);
    // then
    assertEquals(new JsonPrimitive(clientArgs.getKey()), testRequest.getKey());
    assertEquals(clientArgs.getType(), testRequest.getType());
    assertNull(testRequest.getValue());
  }

  @Test
  void parseRequestWithoutKeyAndValueTest() {
    // when
    Mockito.when(clientArgs.getKey()).thenReturn(null);
    Mockito.when(clientArgs.getType()).thenReturn("set");
    Mockito.when(clientArgs.getValue()).thenReturn(null);
    Request testRequest = InputParser.parseRequest(clientArgs);
    // then
    assertEquals(clientArgs.getType(), testRequest.getType());
  }

  @Test
  void shouldReadRequestFromExistingFile() {
    Request request = InputParser.readRequestFromJsonFile("testDelete.json");
    assertNotNull(request);
  }
}
