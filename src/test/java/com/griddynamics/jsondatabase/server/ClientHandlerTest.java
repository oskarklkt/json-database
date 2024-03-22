package com.griddynamics.jsondatabase.server;

import com.griddynamics.jsondatabase.server.socket.ServerConnection;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import java.net.Socket;
import static org.mockito.Mockito.*;

class ClientHandlerTest {

  @Test
  @SneakyThrows
  void runShouldCallInitAndSendAndCloseSocket() {
    // Given
    ServerConnection mockServerConnection = mock(ServerConnection.class);
    Socket mockSocket = mock(Socket.class);
    when(mockServerConnection.getSocket()).thenReturn(mockSocket);

    ClientHandler clientHandler = new ClientHandler(mockServerConnection);

    // When
    clientHandler.start();
    // Then
    verify(mockServerConnection).init();
    verify(mockServerConnection).send();
  }
}
