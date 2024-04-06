package com.griddynamics.jsondatabase.client.socket;

import com.griddynamics.jsondatabase.client.messages.OutputMessages;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

@Getter
@Setter
@Slf4j
public class ClientConnection {
  private static final String address = "127.0.0.1";
  private static final int port = 23456;
  private DataInputStream input;
  private DataOutputStream output;

  public ClientConnection() {
    init();
  }

  @SneakyThrows
  private void init() {
    // can't use try-with-resources, app fails then
    Socket socket = new Socket(InetAddress.getByName(address), port);
    input = new DataInputStream(socket.getInputStream());
    output = new DataOutputStream(socket.getOutputStream());
  }

  @SneakyThrows
  public void send(String message) {
    log.info("{} {}", OutputMessages.SENT.formatted(message), System.lineSeparator());
    output.writeUTF(message);
  }

  @SneakyThrows
  public void receive() {
    String message = input.readUTF();
    log.info("{} {}", OutputMessages.RECEIVED.formatted(message), System.lineSeparator());
  }
}
