package com.griddynamics.jsondatabase.server.socket.factory;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class DefaultServerSocketFactoryTest {

  @Test
  void shouldCreateServerSocket() {
    DefaultServerSocketFactory factory = new DefaultServerSocketFactory();
    ServerSocket serverSocket;
    try {
      serverSocket = factory.createServerSocket(23457, 50, InetAddress.getByName("127.0.0.1"));
      serverSocket.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(serverSocket.getLocalPort(), 23457);
  }
}
