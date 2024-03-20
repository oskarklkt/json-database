package com.griddynamics.jsondatabase.server.socket.Factory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public interface ServerSocketFactory {
    ServerSocket createServerSocket(int port, int backlog, InetAddress address) throws IOException;
}
