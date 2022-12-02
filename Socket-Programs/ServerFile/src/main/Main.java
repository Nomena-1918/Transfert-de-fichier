package main;

import serveur.SocketServeur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        SocketServeur listener = null;
        String fileContent = "";

/// Opening a server socket on port 9999
        try {
            System.out.println("Server is waiting to accept user...");
            listener = new SocketServeur(9999);

            String responseLine;
            while ((responseLine = listener.readMess()) != null) {
                fileContent += responseLine+"\n";
              //  System.out.println(fileContent);
            }
            listener.sendMess("fichier recu");
            listener.stopCom();
        }
            catch (Exception e) {
            throw e;
        }
        System.out.println(fileContent);
        System.out.println("Sever stopped!");

/*
        System.out.println(" Kotrana Scanner : ");
        Scanner myObj = new Scanner(System.in);
        boolean connect = true;

        System.out.println("Enter name:");
        // String input
        String name;
        String liste = "";

        while (connect==true){
            name = myObj.nextLine();
            liste += name+"\n";
            if (name.equalsIgnoreCase("fin")==true) {
                connect=false;
            }
            System.out.println(liste);
        }

        // Output input by user
        System.out.println("Tous les mots entres: \n" + liste);

*/

    }
}
