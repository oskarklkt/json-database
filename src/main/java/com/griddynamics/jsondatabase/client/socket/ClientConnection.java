package com.griddynamics.jsondatabase.client.socket;

import com.griddynamics.jsondatabase.client.messages.OutputMessages;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

  private void init() {
    try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
      input = new DataInputStream(socket.getInputStream());
      output = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void send(String message) {
    try {
      log.info(OutputMessages.SENT.formatted(message) + '\n');
      output.writeUTF(message);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void receive() {
    try {
      String message = input.readUTF();
      log.info(OutputMessages.RECEIVED.formatted(message) + '\n');
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}