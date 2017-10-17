package bio.echo;

import java.io.*;
import java.net.Socket;

/**
 * @author liyebing created on 17/2/16.
 * @version $Id$
 */
public class BIOEchoClient {


    public static void main(String[] args) throws IOException {

        int port = 8082;
        String serverIp = "127.0.0.1";
        Socket socket = null;

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            socket = new Socket(serverIp, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("Hello,java Blocking IO.\n");
            writer.flush();

            String echo = reader.readLine();
            System.out.println("echo:" + echo);

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }

    }


}
