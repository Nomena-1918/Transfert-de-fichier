package client;
import java.io.*;
import java.net.*;


public class SocketClient extends Socket {

    // Server Host = "localhost";
    final String serverHost;
    final int serverPort;

    BufferedWriter os;
    BufferedReader is;
    public SocketClient(String serverHost, int serverPort) throws IOException {
        super(serverHost,
                serverPort);
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        try {
            this.os = new BufferedWriter(new OutputStreamWriter(this.getOutputStream()));
            this.is = new BufferedReader(new InputStreamReader(this.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    /// Read data sent from the server.
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

    public String getServerHost() {
        return serverHost;
    }

    public int getServerPort() {
        return serverPort;
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
