package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    DataInputStream dataInputStream = null;
    BufferedReader is;
    ServerSocket serverSocket;
    Socket clientSocket;


    public Server(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        setServerSocket(serverSocket);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


    public void run() {
        // Here we define Server Socket running on port 900
        try {

            System.out.println("Server is Starting in Port 900");
            // Accept the Client request using accept method
            Socket clientSocket = getServerSocket().accept();
            this.setClientSocket(clientSocket);

            System.out.println("Connected");
            dataInputStream = new DataInputStream(this.getClientSocket().getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(this.getClientSocket().getOutputStream());

            is = new BufferedReader(new InputStreamReader(this.getClientSocket().getInputStream()));

            // Here we call receiveFile define new for that file

            while(!readMess().equalsIgnoreCase("fin")) {
                dataOutputStream = new DataOutputStream(this.getClientSocket().getOutputStream());

                receiveFile("/Users/nomena/Sockets-From-Scratch/TransferFile/ServerFile/src/Files_received/file.txt");
                sendMess("File received by the server");

                dataOutputStream.close();
            }

            this.getClientSocket().close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMess(String mess) throws Exception {
        try {
            Socket s = getClientSocket();
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

            os.write(mess);
            os.newLine();
            os.flush();
        }
        catch (Exception e){
            throw e;
        }
    }

    /// Read data sent from the server.
    public String readMess() throws Exception {
        is = new BufferedReader(new InputStreamReader(getClientSocket().getInputStream()));
        return is.readLine();
    }


    // receive file function is start here
    public void receiveFile(String fileName) throws Exception {
        int bytes;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong(); // read file size

        byte[] buffer = new byte[4 * 1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {

            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read upto file size
        }
        fileOutputStream.close();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
