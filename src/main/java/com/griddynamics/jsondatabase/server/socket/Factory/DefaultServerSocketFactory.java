package com.griddynamics.jsondatabase.server.socket.Factory;

import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.ServerSocket;

public class DefaultServerSocketFactory implements ServerSocketFactory {

  @SneakyThrows
  public ServerSocket createServerSocket(int port, int backlog, InetAddress address) {
    return new ServerSocket(port, backlog, address);
  }
}
