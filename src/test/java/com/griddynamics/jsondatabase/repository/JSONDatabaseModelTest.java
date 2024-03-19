package com.griddynamics.jsondatabase.repository;

import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JSONDatabaseModelTest {

    JSONDatabaseModel jsonDatabaseModel;
    @BeforeEach
    void init() {

        jsonDatabaseModel = new JSONDatabaseModel();
        Map<String, String> testMap = jsonDatabaseModel.getDatabase();
        testMap.put("1", "val1");
        testMap.put("key", "val2");
    }
    @Test
    void setData() {
        jsonDatabaseModel.setData("1", "newVal");
        jsonDatabaseModel.setData("12", "A");
        assertEquals("newVal", jsonDatabaseModel.getData("1"));
        assertEquals("A", jsonDatabaseModel.getData("12"));
    }

    @Test
    void getData() {
        assertEquals("val1", jsonDatabaseModel.getData("1"));
        assertEquals(OutputMessages.ERROR, jsonDatabaseModel.getData("53"));
    }

    @Test
    void deleteData() {
        assertEquals(OutputMessages.ERROR, jsonDatabaseModel.deleteData("NonExistingKey"));
        assertEquals(OutputMessages.OK, jsonDatabaseModel.deleteData("1"));
    }

    @Test
    void testConstructorInitializesDatabase() {
        JSONDatabaseModel model = new JSONDatabaseModel();
        assertTrue(model.getDatabase().isEmpty(), "Database should be empty after initialization");

        model.getDatabase().put("key", "value");
        assertEquals("value", model.getDatabase().get("key"), "Database should return the correct value for a key");
    }
}