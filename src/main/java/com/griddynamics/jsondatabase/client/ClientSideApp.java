package com.griddynamics.jsondatabase.client;

import com.griddynamics.jsondatabase.client.input.ClientArgs;
import com.griddynamics.jsondatabase.client.input.InputParser;
import com.griddynamics.jsondatabase.client.messages.OutputMessages;
import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.client.socket.ClientConnection;
import com.beust.jcommander.JCommander;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientSideApp {
  public static void main(String[] args) {

    log.info(OutputMessages.CLIENT_STARTED);
    ClientConnection clientConnection = new ClientConnection();
    ClientArgs clientArgs = new ClientArgs();
    JCommander.newBuilder().addObject(clientArgs).build().parse(args);
    Request request = InputParser.parseRequest(clientArgs);
    String requestJSON = request.parseJSON();
    clientConnection.send(requestJSON);
    clientConnection.receive();
  }
}
