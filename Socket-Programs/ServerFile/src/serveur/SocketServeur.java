package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServeur extends ServerSocket {
    final int port;
    Socket socketOfServer;
    BufferedWriter os;
    BufferedReader is;

    final String fin = "tapitra";

    public SocketServeur(int port) throws Exception {
        super(port);
        this.port = port;

        try {

            socketOfServer = this.accept();
            System.out.println("Accept a client!");
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
        }
        catch (Exception e) {
            throw e;
        }
    }

    /// Write data to the output stream of the Client Socket.
    public void sendMess(String mess) throws Exception {
        try {
            os.write(mess);
            os.newLine();
            os.flush();
        }
        catch (Exception e){
            throw e;
        }
    }

    /// Read data sent from the client.
    public String readMess() throws Exception {
        String responseLine = is.readLine();
        return responseLine;
    }

    /// Stop communication with the server
    public void stopCom() throws Exception {
        try {
            os.close();
            is.close();
            this.close();
        }
        catch (Exception e) {
            throw e;
        }
    }

    public String getFin() {
        return fin;
    }

    public int getPort() {
        return port;
    }

    public Socket getSocketOfServer() {
        return socketOfServer;
    }

    public void setSocketOfServer(Socket socketOfServer) {
        this.socketOfServer = socketOfServer;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public void setOs(BufferedWriter os) {
        this.os = os;
    }

    public BufferedReader getIs() {
        return is;
    }

    public void setIs(BufferedReader is) {
        this.is = is;
    }
}
