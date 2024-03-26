package com.griddynamics.jsondatabase.server.socket.factory;

import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.ServerSocket;

public interface ServerSocketFactory {
  @SneakyThrows
  ServerSocket createServerSocket(int port, int backlog, InetAddress address);
}
