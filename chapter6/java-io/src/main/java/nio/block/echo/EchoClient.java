package nio.block.echo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liyebing created on 17/2/25.
 * @version $Id$
 */
public class EchoClient {


    public static void main(String[] args) {

        byte[] bytes = "你好,java Blocking I/O !".getBytes();
        ByteBuffer helloBuffer = ByteBuffer.wrap(bytes);
        CharBuffer charBuffer;
        Charset charset = Charset.defaultCharset();
        CharsetDecoder decoder = charset.newDecoder();

        try {
            //创建客户端socketChannel
            SocketChannel socketChannel = SocketChannel.open();
            //如果客户端socketChannel创建成功
            if (socketChannel.isOpen()) {
                //设置为阻塞模式
                socketChannel.configureBlocking(true);
                //设置网络传输参数
                socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                socketChannel.setOption(StandardSocketOptions.SO_LINGER, 5);
                //连接服务端
                socketChannel.connect(new InetSocketAddress("127.0.0.1", 8085));
                //如果成功连接服务端
                if (socketChannel.isConnected()) {
                    //向服务端发送数据
                    socketChannel.write(helloBuffer);
                    //创建接受服务端返回数据ByteBuffer
                    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                    while (socketChannel.read(buffer) != -1) {
                        buffer.flip();
                        charBuffer = decoder.decode(buffer);
                        System.out.println(charBuffer.toString());
                        if (buffer.hasRemaining()) {
                            buffer.compact();
                        } else {
                            buffer.clear();
                        }
                    }
                } else {
                    throw new RuntimeException("connection cannot be established!");
                }

                //关闭连接
                socketChannel.close();
            } else {
                throw new RuntimeException("socket channel cannot be opened!");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    private static byte[] intToByteArray(int n) {
        byte[] byteArray = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);
            dataOut.writeInt(n);
            return byteOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
