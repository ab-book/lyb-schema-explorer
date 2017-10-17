package nio.block.echo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author liyebing created on 17/2/25.
 * @version $Id$
 */
public class EchoHandler implements Runnable {

    private SocketChannel socketChannel;

    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public EchoHandler(SocketChannel socketChannel){
        this.socketChannel=socketChannel;
    }

    public void run() {
        try {
            //读取客户端传输的数据,并原样写入返回给客户端
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();
                socketChannel.write(buffer);
                if (buffer.hasRemaining()) {
                    buffer.compact();
                } else {
                    buffer.clear();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
