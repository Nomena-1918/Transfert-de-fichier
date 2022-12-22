package main;

import client.Client;

public class Main {
    public static void main(String[] args) throws Exception {
        String serverAddress = "localhost";
        int serverPort = 5000;

        //  /Users/nomena/TransferFileTest/ClientFile/src/Files/file1.txt

        Client client = new Client(serverAddress, serverPort);
        client.start();
    }
}
