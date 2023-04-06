package com.cinema.cinema;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class GreetingClient {

    public static void start()
    {
        try (Socket socket = new Socket("localhost", 1111)) {
            System.out.printf("Client: Connected to %s%n", socket.getRemoteSocketAddress());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Client says: 'Hello from %s'%n".formatted(socket.getLocalAddress()));

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println(dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
