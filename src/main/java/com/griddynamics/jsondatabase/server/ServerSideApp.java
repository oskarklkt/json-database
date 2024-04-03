package com.griddynamics.jsondatabase.server;

import com.griddynamics.jsondatabase.client.request.Request;
import com.griddynamics.jsondatabase.controller.Controller;
import com.griddynamics.jsondatabase.controller.JSONDatabaseController;
import com.griddynamics.jsondatabase.repository.JSONFileManager;
import com.griddynamics.jsondatabase.server.input.InputHandler;
import com.griddynamics.jsondatabase.server.messages.*;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.socket.factory.DefaultServerSocketFactory;
import com.griddynamics.jsondatabase.server.socket.factory.ServerSocketFactory;
import com.griddynamics.jsondatabase.server.socket.ServerConnection;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Setter
@Slf4j
public class ServerSideApp {

  static InputHandler inputHandler = new InputHandler();
  static Controller controller = new Controller(new JSONDatabaseController(new JSONFileManager()));
  static ServerConnection serverConnection;
  private static final ServerSocketFactory socketFactory = new DefaultServerSocketFactory();

  public static void main(String[] args) {
    log.info("{}", OutputMessages.SERVER_STARTED);
    startApp();
  }

  @SneakyThrows
  public static void startApp() {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    serverConnection = new ServerConnection(socketFactory);
    do {
      ClientHandler clientHandler = new ClientHandler(serverConnection);
      executorService.execute(clientHandler);
    } while (!serverConnection.isServerClosed());
    serverConnection.server.close();
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
