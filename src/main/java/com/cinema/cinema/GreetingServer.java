package com.cinema.cinema;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GreetingServer extends Thread {

    public GreetingServer()
    {
        Thread t = new Thread(this);
        t.start();
    }


    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(1111)) {
            serverSocket.getInetAddress();
            System.out.println("Server: Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket socket = serverSocket.accept();
            System.out.printf("Server: Connected to client %s%n", socket.getRemoteSocketAddress());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.printf(dataInputStream.readUTF());

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Server says: Thank you for connecting to %s%n".formatted(socket.getLocalAddress() + "\nGoodbye!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
