package com.griddynamics.jsondatabase.client.input;

import com.griddynamics.jsondatabase.client.request.Request;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


class InputParserTest {

    @Mock
    ClientArgs clientArgs = Mockito.mock(ClientArgs.class);
    @Test
    void parseRequestWithAllFieldsTest() {
        //when
        Mockito.when(clientArgs.getKey()).thenReturn("1");
        Mockito.when(clientArgs.getType()).thenReturn("set");
        Mockito.when(clientArgs.getValue()).thenReturn("test");
        Request testRequest = InputParser.parseRequest(clientArgs);
        //then
        assertEquals(clientArgs.getKey(), testRequest.getKey());
        assertEquals(clientArgs.getType(), testRequest.getType());
        assertEquals(clientArgs.getValue(), testRequest.getValue());
    }

    @Test
    void parseRequestWithoutValueTest() {
        //when
        Mockito.when(clientArgs.getKey()).thenReturn("1");
        Mockito.when(clientArgs.getType()).thenReturn("set");
        Mockito.when(clientArgs.getValue()).thenReturn(null);
        Request testRequest = InputParser.parseRequest(clientArgs);
        //then
        assertEquals(clientArgs.getKey(), testRequest.getKey());
        assertEquals(clientArgs.getType(), testRequest.getType());
        assertEquals(clientArgs.getValue(), testRequest.getValue());
    }

    @Test
    void parseRequestWithoutKeyTest() {
        //when
        Mockito.when(clientArgs.getKey()).thenReturn(null);
        Mockito.when(clientArgs.getType()).thenReturn("set");
        Mockito.when(clientArgs.getValue()).thenReturn("test");
        Request testRequest = InputParser.parseRequest(clientArgs);
        //then
        assertEquals(clientArgs.getKey(), testRequest.getKey());
        assertEquals(clientArgs.getType(), testRequest.getType());
        assertEquals(clientArgs.getValue(), testRequest.getValue());
    }

    @Test
    void parseRequestWithoutKeyAndValueTest() {
        //when
        Mockito.when(clientArgs.getKey()).thenReturn(null);
        Mockito.when(clientArgs.getType()).thenReturn("set");
        Mockito.when(clientArgs.getValue()).thenReturn(null);
        Request testRequest = InputParser.parseRequest(clientArgs);
        //then
        assertEquals(clientArgs.getKey(), testRequest.getKey());
        assertEquals(clientArgs.getType(), testRequest.getType());
        assertEquals(clientArgs.getValue(), testRequest.getValue());
    }
}