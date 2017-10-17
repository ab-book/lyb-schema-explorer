package aio.echo;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author liyebing created on 16/2/20.
 * @version $Id$
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncEchoServerHandler> {

    public void completed(AsynchronousSocketChannel result, AsyncEchoServerHandler attachment) {
        //循环接入客户端
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result));
    }

    public void failed(Throwable exc, AsyncEchoServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
