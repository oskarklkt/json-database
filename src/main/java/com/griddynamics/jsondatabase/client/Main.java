package com.griddynamics.jsondatabase.client;

import com.griddynamics.jsondatabase.client.input.ClientArgs;
import com.griddynamics.jsondatabase.client.input.InputParser;
import com.griddynamics.jsondatabase.client.messages.OutputMessages;
import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.client.socket.ClientConnection;
import com.beust.jcommander.JCommander;

public class Main {
    public static void main(String[] args) {
        System.out.println(OutputMessages.CLIENT_STARTED);
        ClientConnection clientConnection = new ClientConnection();
        ClientArgs clientArgs = new ClientArgs();
        JCommander.newBuilder()
                .addObject(clientArgs)
                .build()
                .parse(args);
        Request request = InputParser.parseRequest(clientArgs);
        String requestJSON = request.parseJSON();
        System.out.println(requestJSON);
        clientConnection.send(requestJSON);
        clientConnection.receive();
    }
}
