package main;

import Server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        int serverPort = 900;
        Server s = new Server(serverPort);
        s.start();
    }
}

