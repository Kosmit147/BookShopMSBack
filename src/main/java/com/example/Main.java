package com.example;

import java.io.IOException;

public class Main {
    public static final int port = 666;

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start(port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}