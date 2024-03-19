package com.griddynamics.jsondatabase.controller;

import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.repository.JSONDatabaseModel;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import com.griddynamics.jsondatabase.server.response.ErrorResponse;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.response.ValueResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONDatabaseControllerTest {
    private JSONDatabaseModel database;
    private JSONDatabaseController controller;
    @BeforeEach
    void init() {
        database = new JSONDatabaseModel();
        controller = new JSONDatabaseController(database);
    }

    @Test
    void set() {
        //given
        Request request = new Request("set", "1", "val");
        //when
        controller.set(request);
        //then
        assertEquals(request.getValue(), database.getData(request.getKey()));
        assertEquals(new Response(OutputMessages.OK), controller.set(request));
    }

    @Test
    void get() {

        //given
        database.setData("3", "value");
        Request request1 = new Request("get", "3");
        Request request2 = new Request("get", "2");
        //then
        assertEquals(new ValueResponse(OutputMessages.OK, database.getData("3")), controller.get(request1));
        assertEquals(new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY), controller.get(request2));
    }

    @Test
    void delete() {
        //given
        database.setData("3", "value");
        Request request1 = new Request("delete", "3");
        Request request2 = new Request("delete", "2");
        //then
        assertEquals(new Response(OutputMessages.OK), controller.delete(request1));
        assertEquals(new ErrorResponse(OutputMessages.ERROR, OutputMessages.NO_SUCH_KEY), controller.delete(request2));
    }
}