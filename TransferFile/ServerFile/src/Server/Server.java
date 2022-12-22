package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream = null;
    final int serverPort;

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(serverPort)){
            System.out.println("Listening to port:5000");
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket+" connected\n");
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            String fileServer;
            String choice;
            String mesClient;

            File f;
            while (true) {
                choice = dataInputStream.readUTF();

                if (choice.equalsIgnoreCase("exit"))
                    break;

                //Recevoir fichiers du client
                if (choice.equalsIgnoreCase("PUT")) {
                    System.out.println("\n> Serveur en mode PUT !");
                    fileServer = dataInputStream.readUTF();

                    if (fileServer.equalsIgnoreCase("exit"))
                        break;

                    else {
                        System.out.println("Nom du fichier à créer : " + fileServer);
                        f = new File("/Users/nomena/TransferFileTest/ServerFile/src/FilesReceived/" + fileServer);
                        if (f.createNewFile())
                            dataOutputStream.writeUTF("Fichier créé");
                        else
                            dataOutputStream.writeUTF("Fichier écrasé");
                        receiveFile(f.getPath());
                        System.out.println("Fichier reçu");
                    }
                }

                //Envoyer fichiers du client
                if (choice.equalsIgnoreCase("GET")) {
                    System.out.println("\n> Serveur en mode GET !");
                    fileServer = dataInputStream.readUTF();

                    if (fileServer.equalsIgnoreCase("exit"))
                        break;
                    System.out.println("Nom du fichier à envoyer : " + fileServer);

                    mesClient = dataInputStream.readUTF();
                    if (mesClient.equalsIgnoreCase("exit"))
                        break;

                    sendFile("/Users/nomena/TransferFileTest/ServerFile/src/FilesReceived/" + fileServer);
                    System.out.println("Fichier envoyé");

                }

            }
            clientSocket.close();

        } catch (Exception e){
            try {
                throw e;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
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


}

