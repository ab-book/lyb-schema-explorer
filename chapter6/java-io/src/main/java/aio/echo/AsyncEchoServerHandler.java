package aio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author liyebing created on 16/2/20.
 * @version $Id$
 */
public class AsyncEchoServerHandler implements Runnable {

    private int port;

    CountDownLatch latch;

    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncEchoServerHandler(int port) {
        this.port = port;
        try {
            //获取AsynchronousServerSocketChannel对象
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            //绑定服务端口
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept() {
        //接受客户端连接
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
