package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    final String serverAddress;
    final int serverPort;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }


    public void run()  {
        try(Socket socket = new Socket("localhost",5000)){
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            String fileServer;
            String fileLocal;
            String mesServer;
            String choice;
            while (true) {
                System.out.println("\nVeuillez choisir votre action : (PUT, GET or EXIT )");
                System.out.print(">> ");
                choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("exit")) {
                    dataOutputStream.writeUTF("exit");
                    break;
                }

                //Envoyer fichiers au serveur
                else if (choice.equalsIgnoreCase("PUT")) {
                    dataOutputStream.writeUTF(choice);

                    System.out.println("* PUT choisi ! ");
                    System.out.println("Nom fichier sur serveur : ");
                    System.out.print(">> ");
                    fileServer = scanner.nextLine();
                    dataOutputStream.writeUTF(fileServer);
                    if (fileServer.equalsIgnoreCase("exit"))
                        break;

                    System.out.println("Path du fichier local à envoyer : ");
                    System.out.print(">> ");
                    fileLocal = scanner.nextLine();
                    if (fileLocal.equalsIgnoreCase("exit")) {
                        dataOutputStream.writeUTF(fileServer);
                        break;
                    }
                    sendFile(fileLocal);

                    mesServer = dataInputStream.readUTF();
                    System.out.println(mesServer);
                }

                //Recevoir fichiers du serveur
                else if (choice.equalsIgnoreCase("GET")) {
                    File f;

                    dataOutputStream.writeUTF(choice);
                    System.out.println("* GET choisi ! ");

                    System.out.println("Nom fichier sur serveur : ");
                    System.out.print(">> ");
                    fileServer = scanner.nextLine();
                    if (fileServer.equalsIgnoreCase("exit")) {
                        dataOutputStream.writeUTF(fileServer);
                        break;
                    }
                    dataOutputStream.writeUTF(fileServer);

                    System.out.println("Nom du fichier à recevoir en local : ");
                    System.out.print(">> ");
                    fileLocal = scanner.nextLine();

                    if (fileLocal.equalsIgnoreCase("exit")) {
                        dataOutputStream.writeUTF(fileLocal);
                        break;
                    }

                    else {
                        System.out.println("Nom du fichier à créer : " + fileLocal);
                        f = new File("/Users/nomena/TransferFileTest/ClientFile/src/FilesReceived/" + fileLocal);
                        if (f.createNewFile())
                            dataOutputStream.writeUTF("Fichier créé");
                        else
                            dataOutputStream.writeUTF("Fichier écrasé");
                        receiveFile(f.getPath());
                        System.out.println("Fichier reçu");
                    }
                }
                else System.out.println("Erreur : Commande inexistante");
            }

        }catch (Exception e){
            try {
                throw e;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void sendFile(String path) throws Exception {
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        // send file size
        dataOutputStream.writeLong(file.length());
        // break file into chunks
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }

    public void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }

}
