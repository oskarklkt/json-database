package com.griddynamics.jsondatabase.server.socket;

import com.google.gson.Gson;
import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import com.griddynamics.jsondatabase.server.response.Response;
import com.griddynamics.jsondatabase.server.socket.factory.DefaultServerSocketFactory;
import com.griddynamics.jsondatabase.server.socket.factory.ServerSocketFactory;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ServerConnectionTest {

  @Test
  void constructorShouldInitializeServerSocket() throws IOException {
    // given
    ServerSocketFactory factory = mock(ServerSocketFactory.class);
    ServerSocket serverSocket = mock(ServerSocket.class);
    int port = 23456;
    String address = "127.0.0.1";
    InetAddress inetAddress = InetAddress.getByName(address);

    // When
    when(factory.createServerSocket(eq(port), eq(50), eq(inetAddress))).thenReturn(serverSocket);
    new ServerConnection(factory);
    // Then
    verify(factory).createServerSocket(port, 50, inetAddress);
  }

  @Test
  void isServerClosedShouldReturnTrueAfterExit() throws IOException {
    ServerSocketFactory factory = mock(ServerSocketFactory.class);
    ServerSocket mockServer = mock(ServerSocket.class);
    when(factory.createServerSocket(anyInt(), anyInt(), any())).thenReturn(mockServer);

    ServerConnection serverConnection = new ServerConnection(factory);
    serverConnection.setServer(mockServer);
    serverConnection.setOutput(mock(DataOutputStream.class));
    serverConnection.exit();

    assertTrue(serverConnection.isServerClosed());
    verify(mockServer).close();
  }

  @Test
  void initShouldInitializeStreams() throws IOException {
    // given
    int port = 23456;
    String address = "127.0.0.1";
    InetAddress inetAddress = InetAddress.getByName(address);
    ServerSocketFactory serverSocketFactory = mock(ServerSocketFactory.class);
    ServerSocket serverSocket = mock(ServerSocket.class);
    Socket socket = mock(Socket.class);
    // when
    when(serverSocketFactory.createServerSocket(eq(port), eq(50), eq(inetAddress)))
        .thenReturn(serverSocket);
    when(serverSocket.accept()).thenReturn(socket);
    when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
    when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
    ServerConnection serverConnection = new ServerConnection(serverSocketFactory);
    serverConnection.init();
    // then
    verify(serverSocket).accept();
    assertNotNull(serverConnection.getInput());
    assertNotNull(serverConnection.getOutput());
    serverConnection.server.close();
  }

  @Test
  void initShouldHandleIOException() throws IOException {
    // Given
    int port = 23456;
    String address = "127.0.0.1";
    InetAddress inetAddress = InetAddress.getByName(address);
    // when
    ServerSocketFactory serverSocketFactory = mock(ServerSocketFactory.class);
    ServerSocket serverSocket = mock(ServerSocket.class);
    when(serverSocketFactory.createServerSocket(eq(port), eq(50), eq(inetAddress)))
        .thenReturn(serverSocket);
    when(serverSocket.accept()).thenThrow(new IOException());
    ServerConnection serverConnection = new ServerConnection(serverSocketFactory);
    // then
    assertThrows(IOException.class, serverConnection::init);
    serverConnection.server.close();
  }

  @Test
  void receiveShouldThrowRuntimeExceptionOnIOException() throws IOException {
    // given
    ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
    DataInputStream dataInputStream = new DataInputStream(bais);
    ServerSocketFactory serverSocketFactory = mock(ServerSocketFactory.class);
    bais.close();
    ServerConnection instance = new ServerConnection(serverSocketFactory);
    // when
    instance.setInput(dataInputStream);
    // then
    assertThrows(EOFException.class, instance::receive);
  }

  @Test
  void receiveShouldReturnResponseOnValidInput() throws IOException {
    // given
    String validJson = "{\"type\":\"set\", \"key\":\"1\", \"value\":\"val\"}";
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);
    dos.writeUTF(validJson);
    ByteArrayInputStream byteArrayInputStream =
        new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
    ServerSocketFactory factory = mock(ServerSocketFactory.class);
    ServerSocket mockServerSocket = mock(ServerSocket.class);
    Socket mockSocket = mock(Socket.class);
    // when
    when(factory.createServerSocket(anyInt(), anyInt(), any())).thenReturn(mockServerSocket);
    when(mockServerSocket.accept()).thenReturn(mockSocket);
    when(mockSocket.getInputStream()).thenReturn(dataInputStream);
    ServerConnection instance = new ServerConnection(factory);
    instance.init();
    Response expectedResponse = new Response(OutputMessages.OK);
    Response actualResponse = instance.receive();
    // then
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void exitShouldSendCorrectJsonCloseServerAndSetFlag() throws IOException {
    // given
    ServerSocketFactory factory = mock(ServerSocketFactory.class);
    DataOutputStream mockOutput = mock(DataOutputStream.class);
    ServerSocket mockServer = mock(ServerSocket.class);
    ServerConnection instance = new ServerConnection(factory);
    // when
    instance.setOutput(mockOutput);
    instance.setServer(mockServer);
    instance.exit();
    // then
    verify(mockOutput).writeUTF(new Gson().toJson(new Response(OutputMessages.OK)));
    assertTrue(instance.isServerClosed());
    verify(mockServer).close();
  }

  @Test
  void exitShouldThrowRuntimeExceptionOnIOException() throws IOException {
    DataOutputStream mockOutput = mock(DataOutputStream.class);
    ServerSocket mockServer = mock(ServerSocket.class);
    doThrow(IOException.class).when(mockServer).close();

    ServerConnection instance = new ServerConnection(new DefaultServerSocketFactory());
    instance.setOutput(mockOutput);
    instance.setServer(mockServer);

    assertThrows(IOException.class, instance::exit);
  }

  @Test
  void sendShouldSerializeResponseAndWriteToOutput() throws IOException {
    // given
    ServerConnection serverConnection = mock(ServerConnection.class, CALLS_REAL_METHODS);
    DataOutputStream mockOutput = mock(DataOutputStream.class);
    Response response = new Response(OutputMessages.OK);
    doReturn(response).when(serverConnection).receive();
    serverConnection.setOutput(mockOutput);
    // when
    serverConnection.send();
    // then
    Gson gson = new Gson();
    verify(mockOutput).writeUTF(gson.toJson(response));
  }

  @Test
  void sendShouldHandleIOException() throws IOException {
    // Given
    ServerConnection serverConnection = mock(ServerConnection.class, CALLS_REAL_METHODS);
    DataOutputStream mockOutput = mock(DataOutputStream.class);

    serverConnection.setOutput(mockOutput);

    doThrow(IOException.class)
        .when(mockOutput)
        .writeUTF(new Gson().toJson(new Response(OutputMessages.OK)));

    doReturn(new Response(OutputMessages.OK)).when(serverConnection).receive();

    assertThrows(IOException.class, serverConnection::send);
  }
}
