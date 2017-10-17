package aio.echo;

/**
 * @author liyebing created on  16/2/20.
 * @version $Id$
 */
public class AIOEchoServer {


    public static void main(String[] args) {

        int port = 8080;
        AsyncEchoServerHandler timeServer = new AsyncEchoServerHandler(port);
        new Thread(timeServer).start();
    }

}
