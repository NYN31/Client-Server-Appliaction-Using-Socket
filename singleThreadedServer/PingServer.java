package com.socket.singleThreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PingServer {
    private final int port;

    public PingServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening at 127.0.0.1: " + port);
        listenAndResponseAndAutoDisconnect(serverSocket);
    }

    public void listenAndResponseAndAutoDisconnect(ServerSocket serverSocket) {
        while (!serverSocket.isClosed()) {
            try (Socket socket = serverSocket.accept();
                 BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter response = new PrintWriter(socket.getOutputStream(), true)) {

                String line;
                while ((line = request.readLine()) != null) {
                    System.out.println(line);
                    response.println(line.toUpperCase());
                }
            } catch (IOException e) {
                System.out.println("Client Disconnected...");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PingServer pingServer = new PingServer(6000);
        pingServer.start();
    }
}
