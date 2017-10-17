package nio.echo;

/**
 * @author liyebing created on  16/2/19.
 * @version $Id$
 */
public class NIOEchoServer {


    public static void main(String[] args) {
        int port = 8080;

        NIOEchoServerHandler server = new NIOEchoServerHandler(port);
        new Thread(server).start();
    }


}
