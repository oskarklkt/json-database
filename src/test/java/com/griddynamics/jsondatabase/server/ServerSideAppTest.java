package com.griddynamics.jsondatabase.server;

import com.google.gson.Gson;
import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.controller.JSONDatabaseController;
import com.griddynamics.jsondatabase.server.input.InputHandler;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;

import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.response.ValueResponse;
import com.griddynamics.jsondatabase.server.socket.ServerConnection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServerSideAppTest {

    @Test
    void main() {
    }

    @Test
    void startApp() {
    }

    @Test
    void testManageInputGet() {
        //given
        InputHandler mockInputHandler = mock(InputHandler.class);
        JSONDatabaseController mockController = mock(JSONDatabaseController.class);
        ServerSideApp.inputHandler = mockInputHandler;
        ServerSideApp.controller = mockController;
        Request getRequest = new Request("get", "key1", null);
        ValueResponse getResponse = new ValueResponse(OutputMessages.OK, "value1");
        Gson gson = new Gson();
        //when
        when(mockInputHandler.handleInput(gson.toJson(getRequest))).thenReturn(getRequest);
        when(mockController.get(getRequest)).thenReturn(getResponse);
        //then
        Response response = ServerSideApp.manageInput(gson.toJson(getRequest));
        assertNotNull(response);
        assertEquals("value1", ((ValueResponse)response).getValue());
        verify(mockController).get(getRequest);
    }

    @Test
    void testManageInputSet() {
        //given
        InputHandler mockInputHandler = mock(InputHandler.class);
        JSONDatabaseController mockController = mock(JSONDatabaseController.class);
        ServerSideApp.inputHandler = mockInputHandler;
        ServerSideApp.controller = mockController;
        Request getRequest = new Request("set", "key1", "test");
        Response getResponse = new Response(OutputMessages.OK);
        Gson gson = new Gson();
        //when
        when(mockInputHandler.handleInput(gson.toJson(getRequest))).thenReturn(getRequest);
        when(mockController.set(getRequest)).thenReturn(getResponse);
        //then
        Response response = ServerSideApp.manageInput(gson.toJson(getRequest));
        assertNotNull(response);
        assertEquals(OutputMessages.OK, response.getResponse());
        verify(mockController).set(getRequest);
    }

    @Test
    void testManageInputDelete() {
        //given
        InputHandler mockInputHandler = mock(InputHandler.class);
        JSONDatabaseController mockController = mock(JSONDatabaseController.class);
        ServerSideApp.inputHandler = mockInputHandler;
        ServerSideApp.controller = mockController;
        Request getRequest = new Request("delete", "key1");
        Response getResponse = new Response(OutputMessages.OK);
        Gson gson = new Gson();
        //when
        when(mockInputHandler.handleInput(gson.toJson(getRequest))).thenReturn(getRequest);
        when(mockController.delete(getRequest)).thenReturn(getResponse);
        //then
        Response response = ServerSideApp.manageInput(gson.toJson(getRequest));
        assertNotNull(response);
        assertEquals(OutputMessages.OK, response.getResponse());
        verify(mockController).delete(getRequest);
    }

    @Test
    void testManageInputInvalid() {
        //given
        InputHandler mockInputHandler = mock(InputHandler.class);
        JSONDatabaseController mockController = mock(JSONDatabaseController.class);
        ServerSideApp.inputHandler = mockInputHandler;
        ServerSideApp.controller = mockController;
        ServerConnection mockServerConnection = mock(ServerConnection.class);
        ServerSideApp.serverConnection = mockServerConnection;
        Request getRequest = new Request("invalid", "key1");
        Gson gson = new Gson();
        //when
        when(mockInputHandler.handleInput(gson.toJson(getRequest))).thenReturn(getRequest);
        ServerSideApp.manageInput(gson.toJson(getRequest));
        //then
        verify(mockServerConnection).exit();
    }
}
