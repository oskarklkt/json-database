package com.griddynamics.jsondatabase.server.socket.Factory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class DefaultServerSocketFactory implements ServerSocketFactory {

  public ServerSocket createServerSocket(int port, int backlog, InetAddress address)
      throws IOException {
    return new ServerSocket(port, backlog, address);
  }
}
