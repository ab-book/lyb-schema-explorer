package aio.echo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author liyebing created on 16/3/26.
 * @version $Id$
 */
public class AsyncEchoClientHandler implements CompletionHandler<Void, AsyncEchoClientHandler>, Runnable {


    private AsynchronousSocketChannel client;

    private String host;

    private int port;

    private CountDownLatch latch;

    public AsyncEchoClientHandler(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            client = AsynchronousSocketChannel.open();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {

        latch = new CountDownLatch(1);
        client.connect(new InetSocketAddress(host, port), this, this);

        try {
            latch.await();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void completed(Void result, AsyncEchoClientHandler attachment) {
        //准备写入服务端的数据
        byte[] req = "你好,java Asynchronous IO.".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();

        //将数据写入到服务端
        client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            //客户端数据写入服务端写入完成
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                    client.write(buffer, buffer, this);
                } else {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    //读取从服务端传回的数据
                    client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        //服务端数据成功传回处理逻辑
                        public void completed(Integer result, ByteBuffer buffer) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);

                            String body;
                            try {
                                //将服务端传回的数据打印到控制台
                                body = new String(bytes, "UTF-8");
                                System.out.println("echo content is:" + body);

                                latch.countDown();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //服务端数据返回出错
                        public void failed(Throwable exc, ByteBuffer buffer) {
                            try {
                                client.close();
                                latch.countDown();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            //客户端数据写入服务端写入出错
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    client.close();
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void failed(Throwable exc, AsyncEchoClientHandler attachment) {
        try {
            client.close();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
