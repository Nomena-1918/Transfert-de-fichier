package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client extends Thread {
    String serverAdress;
    int serverPort;
    Socket socket;
    DataOutputStream dataOutputStream = null;

    public Client(String serverAdress, int serverPort) {
        setServerAdress(serverAdress);
        setServerPort(serverPort);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        // Create Client Socket connect to port 900
        try {
            Socket socket = new Socket(getServerAdress(), getServerPort());
            setSocket(socket);

            DataOutputStream dataOutputStream = new DataOutputStream(getSocket().getOutputStream());
            setDataOutputStream(dataOutputStream);


            Scanner myObj = new Scanner(System.in);
            System.out.println("Path of the File to transfer to the Server : ");

            // String input
            String path;

            String s = "fin";

            while (true) {
                path = myObj.nextLine();

                if (path.equalsIgnoreCase(s)) {
                    sendMess(s);
                    break;
                }
                else if (path.equals("") || path.equals("\n"))
                  break;

                sendFile(path);

                System.out.println(readMess());

            }

            getDataOutputStream().close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /// Read data sent from the server.
    public String readMess() throws Exception {
        BufferedReader is = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
        return is.readLine();
    }

    public Socket getSocket() {
        return socket;
    }

    public void sendMess(String mess) throws Exception {
        try {
            Socket s = getSocket();
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

            os.write(mess);
            os.newLine();
            os.flush();
        }
        catch (Exception e){
            throw e;
        }
    }


    // sendFile function define here
    public void sendFile(String path) throws Exception {
        int bytes;
        // Open the File where he located in your pc
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        // Here we send the File to Server
        getDataOutputStream().writeLong(file.length());
        // Here we  break file into chunks
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            // Send the file to Server Socket
            getDataOutputStream().write(buffer, 0, bytes);
            getDataOutputStream().flush();
        }
        // close the file here
        fileInputStream.close();
    }

    public String getServerAdress() {
        return serverAdress;
    }

    public void setServerAdress(String serverAdress) {
        this.serverAdress = serverAdress;
    }
    public int getServerPort() {
        return serverPort;
    }
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
