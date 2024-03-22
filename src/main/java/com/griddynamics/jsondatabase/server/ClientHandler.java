package com.griddynamics.jsondatabase.server;

import com.griddynamics.jsondatabase.server.socket.ServerConnection;

import java.io.IOException;

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

  public void closeSocket() {
    try {
      serverConnection.socket.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
