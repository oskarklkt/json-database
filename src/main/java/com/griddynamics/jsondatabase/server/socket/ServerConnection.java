package com.griddynamics.jsondatabase.server.socket;

import com.google.gson.Gson;
import com.griddynamics.jsondatabase.server.ServerSideApp;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.socket.Factory.ServerSocketFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Setter
@Getter
public class ServerConnection {
  private static final String address = "127.0.0.1";
  private static final int port = 23456;
  private final ServerSocketFactory serverSocketFactory;
  public ServerSocket server;
  public Socket socket;
  private DataInputStream input;
  private DataOutputStream output;
  boolean isServerClosed = false;

  @SneakyThrows
  public ServerConnection(ServerSocketFactory factory) {
    this.serverSocketFactory = factory;
    this.server = serverSocketFactory.createServerSocket(port, 50, InetAddress.getByName(address));
  }

  @SneakyThrows
  public void init() {
    socket = server.accept();
    input = new DataInputStream(socket.getInputStream());
    output = new DataOutputStream(socket.getOutputStream());
  }

  public boolean isServerClosed() {
    return isServerClosed;
  }

  @SneakyThrows
  public void send() {
    Response s = receive();
    Gson gson = new Gson();
    output.writeUTF(gson.toJson(s));
  }

  @SneakyThrows
  public void exit() {
    Response s = new Response(OutputMessages.OK);
    isServerClosed = true;
    Gson gson = new Gson();
    output.writeUTF(gson.toJson(s));
    server.close();
  }

  @SneakyThrows
  public Response receive() {
    return ServerSideApp.manageInput(input.readUTF());
  }
}
