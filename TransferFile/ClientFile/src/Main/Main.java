package Main;

import client.Client;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
/*
        String pathFile = "/Users/nomena/Sockets-From-Scratch/TransferFile/ClientFile/f2.txt";
        String serverAdress = "localhost";
        int serverPort = 900;

        Client c = new Client(serverAdress, serverPort);
        c.start();
*/
        long l =System.currentTimeMillis();

        Timestamp t1 = new Timestamp(l);

        long l1 = 1671365610110L;
        Timestamp t2 = new Timestamp(l1);

        System.out.println("Date1 : "+t1);
        System.out.println("Date2 : "+t2);

        System.out.println("Date1 - Date2 : "+(t1.getTime()- t2.getTime())/1000+" s");
    }
}
