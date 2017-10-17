package nio.echo;

/**
 * @author liyebing created on 16/2/20.
 * @version $Id$
 */
public class NIOEchoClient {

    public static void main(String[] args) {

        int port = 8080;
        new Thread(new NIOClientHandler("127.0.0.1", port)).start();
    }

}
