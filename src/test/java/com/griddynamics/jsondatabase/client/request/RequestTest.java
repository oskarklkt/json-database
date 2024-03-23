package com.griddynamics.jsondatabase.client.request;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

  @Test
  void shouldSerializeRequestToJson() {
    Request request =
        new Request("testType", new JsonPrimitive("testKey"), new JsonPrimitive("testValue"));
    String json = request.parseJSON();
    assertEquals("{\"type\":\"testType\",\"key\":\"testKey\",\"value\":\"testValue\"}", json);
  }

  @Test
  void shouldSerializeRequestWithNullFieldsToJson() {
    Request request = new Request("testType", null, null);
    String json = request.parseJSON();
    assertEquals("{\"type\":\"testType\"}", json);
  }

  @Test
  void equalsShouldBeSymmetric() {
    Request request1 = new Request("type", new JsonPrimitive("key"), new JsonPrimitive("value"));
    Request request2 = new Request("type", new JsonPrimitive("key"), new JsonPrimitive("value"));
    assertTrue(request1.equals(request2) && request2.equals(request1));
  }

  @Test
  void equalsShouldBeTransitive() {
    Request request1 = new Request("type", new JsonPrimitive("key"), new JsonPrimitive("value"));
    Request request2 = new Request("type", new JsonPrimitive("key"), new JsonPrimitive("value"));
    Request request3 = new Request("type", new JsonPrimitive("key"), new JsonPrimitive("value"));
    assertTrue(request1.equals(request2) && request2.equals(request3) && request1.equals(request3));
  }

  @Test
  void equalsShouldReturnFalseForNull() {
    Request request = new Request("type");
    assertNotEquals(null, request);
  }

  @Test
  void hashCodeShouldBeConsistent() {
    Request request = new Request("type");
    int initialHashCode = request.hashCode();
    assertEquals(initialHashCode, request.hashCode());
  }

  @Test
  void equalObjectsShouldHaveEqualHashCodes() {
    Request request1 = new Request("type", new JsonPrimitive("key"), new JsonPrimitive("value"));
    Request request2 = new Request("type", new JsonPrimitive("key"), new JsonPrimitive("value"));
    assertTrue(request1.equals(request2) && (request1.hashCode() == request2.hashCode()));
  }
}
