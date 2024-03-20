package com.griddynamics.jsondatabase.server.socket;

import com.google.gson.Gson;
import com.griddynamics.jsondatabase.server.ServerSideApp;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.socket.Factory.ServerSocketFactory;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


@Getter
public class ServerConnection {
    private static final String address = "127.0.0.1";
    private static final int port = 23456;
    private final ServerSocketFactory serverSocketFactory;
    public ServerSocket server;
    public Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean isServerClosed = false;

    public ServerConnection(ServerSocketFactory factory) throws IOException {
        this.serverSocketFactory = factory;
        this.server = serverSocketFactory.createServerSocket(port, 50, InetAddress.getByName(address));
    }

    public void init() {
        try {
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isServerClosed() {
        return isServerClosed;
    }

    public void send() {
        try {
            Response s = receive();
            Gson gson = new Gson();
            output.writeUTF(gson.toJson(s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exit() {
        try {
            Response s = new Response(OutputMessages.OK);
            isServerClosed = true;
            Gson gson = new Gson();
            output.writeUTF(gson.toJson(s));
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response receive() {
        try {
            return ServerSideApp.manageInput(input.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
