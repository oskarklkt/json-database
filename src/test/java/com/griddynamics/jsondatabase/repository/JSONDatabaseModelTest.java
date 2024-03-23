package com.griddynamics.jsondatabase.repository;

import com.google.gson.JsonObject;

import com.google.gson.JsonPrimitive;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JSONDatabaseModelTest {
  @Test
  void constructorShouldInitializeFieldsProperly() {
    // constructor creates writeLock and readLock, that's why we only check if it compiles right
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.getReadLock().lock();
    model.getReadLock().unlock();
    model.getWriteLock().lock();
    model.getWriteLock().unlock();
  }

  @Test
  void shouldReturnElementWhenKeyIsJsonPrimitiveAndExists() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    JsonObject mockDatabase = new JsonObject();
    mockDatabase.addProperty("key", "value");
    // when
    when(mockFileManager.updateDatabase()).thenReturn(mockDatabase);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonElement key = new JsonPrimitive("key");
    JsonElement result = model.getData(key);
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
    when(mockFileManager.updateDatabase()).thenReturn(mockDatabase);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonElement key = new JsonPrimitive("nonexistentKey");
    JsonElement result = model.getData(key);
    // then
    assertNull(result);
  }

  @Test
  void shouldReturnNullWhenKeysDoNotExistInJsonArrayAndNotCreating() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    JsonObject mockDatabase = new JsonObject();
    JsonObject intermediateObject = new JsonObject();
    mockDatabase.add("existentElement", intermediateObject);
    // when
    when(mockFileManager.updateDatabase()).thenReturn(mockDatabase);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonArray arrayKey = new JsonArray();
    arrayKey.add("existentElement");
    arrayKey.add("nonexistentNestedElement");
    JsonElement result = model.getData(arrayKey);
    // then
    assertNull(result);
  }

  @Test
  void shouldCreateDatabaseAndSetDataIfDatabaseIsNull() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    // when
    when(mockFileManager.updateDatabase()).thenReturn(null);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonPrimitive key = new JsonPrimitive("key");
    JsonPrimitive value = new JsonPrimitive("value");
    model.setData(key, value);
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
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    JsonObject mockDatabase = new JsonObject();
    // when
    when(mockFileManager.updateDatabase()).thenReturn(mockDatabase);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonPrimitive key = new JsonPrimitive("key");
    JsonPrimitive value = new JsonPrimitive("value");
    model.setData(key, value);
    // then
    verify(mockFileManager).updateJSON(mockDatabase);
    assertEquals(value, mockDatabase.get(key.getAsString()));
  }

  @Test
  void shouldSetDataWithJsonArrayKey() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    JsonObject mockDatabase = new JsonObject();
    // when
    when(mockFileManager.updateDatabase()).thenReturn(mockDatabase);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonArray key = new JsonArray();
    key.add("level1");
    key.add("level2");
    JsonPrimitive value = new JsonPrimitive("value");
    model.setData(key, value);
    // then
    verify(mockFileManager).updateJSON(mockDatabase);
    assertNotNull(mockDatabase.getAsJsonObject("level1").get("level2"));
    assertEquals(value, mockDatabase.getAsJsonObject("level1").get("level2"));
  }

  @Test
  void shouldDeleteDataWithJsonPrimitiveKey() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    JsonObject mockDatabase = new JsonObject();
    mockDatabase.addProperty("key", "value");
    // when
    when(mockFileManager.updateDatabase()).thenReturn(mockDatabase);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonPrimitive key = new JsonPrimitive("key");
    String result = model.deleteData(key);
    // then
    assertEquals(OutputMessages.OK, result);
    assertFalse(mockDatabase.has("key"));
    verify(mockFileManager).updateJSON(mockDatabase);
  }

  @Test
  void shouldNotDeleteDataWhenJsonPrimitiveKeyDoesNotExist() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    JsonObject mockDatabase = new JsonObject();
    // when
    when(mockFileManager.updateDatabase()).thenReturn(mockDatabase);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonPrimitive key = new JsonPrimitive("key");
    String result = model.deleteData(key);
    // then
    assertEquals(OutputMessages.ERROR, result);
  }

  @Test
  void shouldDeleteDataWithJsonArrayKey() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    JsonObject nestedObject = new JsonObject();
    nestedObject.addProperty("toDelete", "value");
    JsonObject mockDatabase = new JsonObject();
    mockDatabase.add("level1", nestedObject);
    // when
    when(mockFileManager.updateDatabase()).thenReturn(mockDatabase);
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonArray key = new JsonArray();
    key.add("level1");
    key.add("toDelete");
    String result = model.deleteData(key);
    // when
    assertEquals(OutputMessages.OK, result);
    assertFalse(nestedObject.has("toDelete"));
    verify(mockFileManager).updateJSON(mockDatabase);
  }

  @Test
  void shouldReturnErrorForInvalidKeyType() {
    // given
    JSONFileManager mockFileManager = mock(JSONFileManager.class);
    // when
    when(mockFileManager.updateDatabase()).thenReturn(new JsonObject());
    JSONDatabaseModel model = new JSONDatabaseModel();
    model.setJsonFileManager(mockFileManager);
    JsonElement key = new JsonObject();
    String result = model.deleteData(key);
    // then
    assertEquals(OutputMessages.ERROR, result);
  }
}
