package com.griddynamics.jsondatabase.server.socket;

import com.google.gson.Gson;
import com.griddynamics.jsondatabase.server.ServerSideApp;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import com.griddynamics.jsondatabase.server.response.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {
    private static final String address = "127.0.0.1";
    private static final int port = 23456;
    public ServerSocket server;
    public Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    public static boolean isServerClosed = false;

    public ServerConnection() throws IOException {
        this.server = new ServerSocket(port, 50, InetAddress.getByName(address));
    }
    public void init()  {
        try {
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            output  = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void send() {
        try {
            Response s = receive();
            Gson gson = new Gson();
            output.writeUTF(gson.toJson(s));
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exit() {
        try {
            String s = OutputMessages.OK;
            isServerClosed = true;
            output.writeUTF(s);
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
