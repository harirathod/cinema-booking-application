package com.cinema.cinema;

public class GreetingNetworkDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        GreetingServer greetingServer = new GreetingServer();

        GreetingClient.start();
        System.out.println(System.currentTimeMillis() - start);
    }
}
