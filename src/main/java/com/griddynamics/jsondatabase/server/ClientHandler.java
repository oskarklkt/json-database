package com.griddynamics.jsondatabase.server;

import com.griddynamics.jsondatabase.server.socket.ServerConnection;
import lombok.SneakyThrows;



public class ClientHandler extends Thread {
  final ServerConnection serverConnection;

  public ClientHandler(ServerConnection serverConnection) {
    this.serverConnection = serverConnection;
  }

  @Override
  public void run() {
    serverConnection.init();
    serverConnection.send();
    if (serverConnection.socket != null) {
      closeSocket();
    }
  }

  @SneakyThrows
  public void closeSocket() {
    serverConnection.socket.close();
  }
}
