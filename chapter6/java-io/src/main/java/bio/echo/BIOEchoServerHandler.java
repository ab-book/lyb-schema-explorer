package bio.echo;

import java.io.*;
import java.net.Socket;

/**
 * @author liyebing created on 17/2/16.
 * @version $Id$
 */
public class BIOEchoServerHandler implements Runnable {

    private Socket socket;

    public BIOEchoServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                writer.write(line + "\n");
                writer.flush();
            }
        } catch (Exception e) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }
}
