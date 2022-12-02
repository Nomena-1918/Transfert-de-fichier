package main;

import client.SocketClient;

import java.io.File;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws Exception {

        final String serverHost = "localhost";
        final int serverPort = 9999;
        SocketClient socketOfClient1 = null;
        String fileContent="";

        try {
            File myObj = new File("E:\\S3\\Reseaux\\Socket-Programs\\ClientFile\\src\\main\\simpleFile.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                fileContent += myReader.nextLine()+"\n";
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            throw e;
        }

        try {
            socketOfClient1 = new SocketClient(serverHost, serverPort);

            //Envoi contenu du fichier vers le serveur
            socketOfClient1.sendMess(fileContent);

            String responseLine;
            while ((responseLine = socketOfClient1.readMess()) != null) {
                System.out.println("Server: >> " + responseLine);
            }

            socketOfClient1.stopCom();
        } catch (Exception e) {
            throw e;
        }
    }
}
