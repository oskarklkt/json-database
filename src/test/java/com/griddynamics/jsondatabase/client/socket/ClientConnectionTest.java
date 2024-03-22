package com.griddynamics.jsondatabase.client.socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.net.Socket;
import org.junit.jupiter.api.Test;

class ClientConnectionTest {

  @Test
  void initShouldInitializeStreams() throws IOException {
    // given
    Socket mockSocket = mock(Socket.class);
    InputStream mockInputStream = mock(InputStream.class);
    OutputStream mockOutputStream = mock(OutputStream.class);
    // when
    when(mockSocket.getInputStream()).thenReturn(mockInputStream);
    when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);
    ClientConnection instance = spy(new ClientConnection());
    // then
    assertNotNull(instance.getInput());
    assertNotNull(instance.getOutput());
    mockSocket.close();
  }

  @Test
  void sendShouldWriteMessageToOutputStream() throws IOException {
    // given
    DataOutputStream mockOutput = mock(DataOutputStream.class);
    ClientConnection clientConnection = new ClientConnection();
    clientConnection.setOutput(mockOutput);
    String testMessage = "Hello, World!";
    // when
    clientConnection.send(testMessage);
    // then
    verify(mockOutput).writeUTF(testMessage);
  }
}
