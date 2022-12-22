package main;

import Server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        int serverPort = 5000;

        Server server = new Server(serverPort);
        server.start();
    }
}

