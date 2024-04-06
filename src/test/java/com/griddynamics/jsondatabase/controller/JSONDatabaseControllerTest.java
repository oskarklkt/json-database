package com.griddynamics.jsondatabase.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.griddynamics.jsondatabase.repository.JSONFileManager;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JSONDatabaseControllerTest {

  JSONDatabaseController controller;
  JSONFileManager mockFileManager;

  @BeforeEach
  void setUp() {
    mockFileManager = mock(JSONFileManager.class);
    controller = new JSONDatabaseController(mockFileManager);
  }

  @Test
  void constructorShouldInitializeFieldsProperly() {
    // constructor creates writeLock and readLock, that's why we only check if it compiles right
    JSONDatabaseController controller = new JSONDatabaseController(mockFileManager);
    controller.getReadLock().lock();
    controller.getReadLock().unlock();
    controller.getWriteLock().lock();
    controller.getWriteLock().unlock();
  }

  @Test
  void shouldReturnElementWhenKeyIsJsonPrimitiveAndExists() {
    // given
    JsonObject mockDatabase = new JsonObject();
    mockDatabase.addProperty("key", "value");
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(mockDatabase));
    JsonElement key = new JsonPrimitive("key");
    JsonElement result = controller.getData(key);
    // then
    assertNotNull(result);
    assertEquals("value", result.getAsString());
  }

  @Test
  void shouldReturnNullWhenKeyIsJsonPrimitiveAndDoesNotExist() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    JsonObject mockDatabase = new JsonObject();
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(mockDatabase));
    JsonElement key = new JsonPrimitive("nonexistentKey");
    JsonElement result = controller.getData(key);
    // then
    assertNull(result);
  }

  @Test
  void shouldReturnNullWhenKeysDoNotExistInJsonArrayAndNotCreating() {
    // given
    JsonObject mockDatabase = new JsonObject();
    JsonObject intermediateObject = new JsonObject();
    mockDatabase.add("existentElement", intermediateObject);
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(mockDatabase));
    JsonArray arrayKey = new JsonArray();
    arrayKey.add("existentElement");
    arrayKey.add("nonexistentNestedElement");
    JsonElement result = controller.getData(arrayKey);
    // then
    assertNull(result);
  }

  @Test
  void shouldCreateDatabaseAndSetDataIfDatabaseIsNull() {
    // when
    when(mockFileManager.updateDatabase()).thenReturn(null);
    JsonPrimitive key = new JsonPrimitive("key");
    JsonPrimitive value = new JsonPrimitive("value");
    controller.setData(key, value);
    ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
    verify(mockFileManager).updateJSON(captor.capture());
    JsonObject updatedDatabase = captor.getValue();
    // then
    assertTrue(updatedDatabase.has(key.getAsString()));
    assertEquals(value, updatedDatabase.get(key.getAsString()));
  }

  @Test
  void shouldSetDataWithJsonPrimitiveKey() {
    // given
    JsonObject mockDatabase = new JsonObject();
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(mockDatabase));
    JsonPrimitive key = new JsonPrimitive("key");
    JsonPrimitive value = new JsonPrimitive("value");
    controller.setData(key, value);
    // then
    verify(mockFileManager).updateJSON(mockDatabase);
    assertEquals(value, mockDatabase.get(key.getAsString()));
  }

  @Test
  void shouldSetDataWithJsonArrayKey() {
    // given
    JsonObject mockDatabase = new JsonObject();
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(mockDatabase));
    JsonArray key = new JsonArray();
    key.add("level1");
    key.add("level2");
    JsonPrimitive value = new JsonPrimitive("value");
    controller.setData(key, value);
    // then
    verify(mockFileManager).updateJSON(mockDatabase);
    assertNotNull(mockDatabase.getAsJsonObject("level1").get("level2"));
    assertEquals(value, mockDatabase.getAsJsonObject("level1").get("level2"));
  }

  @Test
  void shouldDeleteDataWithJsonPrimitiveKey() {
    // given
    JsonObject mockDatabase = new JsonObject();
    mockDatabase.addProperty("key", "value");
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(mockDatabase));
    JsonPrimitive key = new JsonPrimitive("key");
    String result = controller.deleteData(key);
    // then
    assertEquals(OutputMessages.OK, result);
    assertFalse(mockDatabase.has("key"));
    verify(mockFileManager).updateJSON(mockDatabase);
  }

  @Test
  void shouldNotDeleteDataWhenJsonPrimitiveKeyDoesNotExist() {
    // given
    JsonObject mockDatabase = new JsonObject();
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(mockDatabase));
    JsonPrimitive key = new JsonPrimitive("key");
    String result = controller.deleteData(key);
    // then
    assertEquals(OutputMessages.ERROR, result);
  }

  @Test
  void shouldDeleteDataWithJsonArrayKey() {
    // given
    JsonObject nestedObject = new JsonObject();
    nestedObject.addProperty("toDelete", "value");
    JsonObject mockDatabase = new JsonObject();
    mockDatabase.add("level1", nestedObject);
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(mockDatabase));
    JsonArray key = new JsonArray();
    key.add("level1");
    key.add("toDelete");
    String result = controller.deleteData(key);
    // when
    assertEquals(OutputMessages.OK, result);
    assertFalse(nestedObject.has("toDelete"));
    verify(mockFileManager).updateJSON(mockDatabase);
  }

  @Test
  void shouldReturnErrorForInvalidKeyType() {
    // when
    when(mockFileManager.updateDatabase()).thenReturn(Optional.of(new JsonObject()));
    JsonElement key = new JsonObject();
    String result = controller.deleteData(key);
    // then
    assertEquals(OutputMessages.ERROR, result);
  }
}
