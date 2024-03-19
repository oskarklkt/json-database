package com.griddynamics.jsondatabase.client.request;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {


    @Test
    void parseJSONSetTest() {
        //given
        Request request = new Request("set", "Secret key", "Secret value");
        //when
        String expected = "{\"type\":\"set\",\"key\":\"Secret key\",\"value\":\"Secret value\"}";
        String actual = request.parseJSON();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void parseJSONGetTest() {
        //given
        Request request = new Request("get", "Secret key");
        //when
        String expected = "{\"type\":\"get\",\"key\":\"Secret key\"}";
        String actual = request.parseJSON();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void parseJSONDeleteTest() {
        //given
        Request request = new Request("delete", "Key");
        //when
        String expected = "{\"type\":\"delete\",\"key\":\"Key\"}";
        String actual = request.parseJSON();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void hashCodeTest() {
        assertEquals(Objects.hash("set", "1", "val"),new Request("set", "1", "val").hashCode());
    }
}