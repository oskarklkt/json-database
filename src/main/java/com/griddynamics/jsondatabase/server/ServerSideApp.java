package com.griddynamics.jsondatabase.server;

import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.controller.JSONDatabaseController;
import com.griddynamics.jsondatabase.repository.JSONDatabaseModel;
import com.griddynamics.jsondatabase.server.input.InputHandler;
import com.griddynamics.jsondatabase.server.messages.*;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.socket.ServerConnection;

import java.io.IOException;


public class ServerSideApp {

    static InputHandler inputHandler = new InputHandler();
    static JSONDatabaseController controller = new JSONDatabaseController(new JSONDatabaseModel());
    static ServerConnection serverConnection;


    public static void main(String[] args) {
        System.out.println(OutputMessages.SERVER_STARTED);
        startApp();
    }

    public static void startApp() {
        do {
            try {
                serverConnection = new ServerConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            serverConnection.init();
            serverConnection.send();
        } while (!ServerConnection.isServerClosed);
    }

    public static Response manageInput(String input) {
        Request command = inputHandler.handleInput(input);
        return processCommand(command);
    }

    static Response processCommand(Request command) {
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
            default -> {
                serverConnection.exit();
                return null;
            }
        }
    }
}
