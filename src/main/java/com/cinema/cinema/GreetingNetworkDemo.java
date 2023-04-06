package com.cinema.cinema;

public class GreetingNetworkDemo {

    public static void main(String[] args) {
        GreetingServer greetingServer = new GreetingServer();

        GreetingClient.start();
    }
}
