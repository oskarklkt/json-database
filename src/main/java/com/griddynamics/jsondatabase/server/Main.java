package com.griddynamics.jsondatabase.server;

import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.controller.JSONDatabaseController;
import com.griddynamics.jsondatabase.repository.JSONDatabaseModel;
import com.griddynamics.jsondatabase.server.input.InputHandler;
import com.griddynamics.jsondatabase.server.messages.*;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.socket.ServerConnection;


public class Main {

    private static final JSONDatabaseController controller = new JSONDatabaseController(new JSONDatabaseModel());
    private static ServerConnection serverConnection;

    public static void main(String[] args) {
        System.out.println(OutputMessages.SERVER_STARTED);
        do {
            serverConnection = new ServerConnection();
            serverConnection.init();
            serverConnection.send();
        } while (!ServerConnection.isServerClosed);
    }

    public static Response start(String input) {
        Request command = InputHandler.handleInput(input);
        String action = command.getType();
        switch (action) {
            case InputMessages.GET -> {
                return controller.get(command);
            }
            case InputMessages.SET -> {
                return controller.set(command);
            }
            case InputMessages.DELETE -> {
                return controller.delete(command);
            }
            case InputMessages.EXIT -> serverConnection.exit();
        }
        return null;
    }
}
