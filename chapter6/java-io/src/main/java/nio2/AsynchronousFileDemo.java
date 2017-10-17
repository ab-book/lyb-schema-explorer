package nio2;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author liyebing created on 17/3/4.
 * @version $Id$
 */
public class AsynchronousFileDemo {


    //使用CompletionHandler异步读文件
    @Test
    public void testCompletionHandler() throws IOException, InterruptedException {
        //打开文件通道,获取异步Channel对象
        AsynchronousFileChannel ch = AsynchronousFileChannel.open(Paths.get("/Users/liyebing/p.txt"), StandardOpenOption.READ);
        //读取文件临时ByteBuffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //文件读取位置
        long position = 0;
        //文件读取结果计数器,保存每次读取文件是否读完或者读失败
        final List<Integer> byteReadResultHolder = new ArrayList<Integer>(1);

        //文件读取结果内容
        List<Byte> totalByteList = new ArrayList<Byte>();
        while (true) {
            //每次开始读之前,先重置读取结果计数器
            byteReadResultHolder.clear();

            //异步读文件
            final CountDownLatch latch = new CountDownLatch(1);
            ch.read(buffer, position, null, new CompletionHandler<Integer, Void>() {
                public void completed(Integer result, Void attachment) {
                    //保存本次读取的结果,若result =-1 ,则代表数据已经读完
                    byteReadResultHolder.add(result);
                    latch.countDown();
                }

                public void failed(Throwable exc, Void attachment) {
                    //读文件失败,打印失败结果
                    System.out.println("read failure:" + exc.toString());
                    latch.countDown();
                }
            });
            //等待异步文件读取操作完成
            latch.await();

            //如果读取完毕,则直接退出
            if (byteReadResultHolder.size() <= 0 || (byteReadResultHolder.size() > 0 && byteReadResultHolder.get(0) == -1)) {
                break;
            }

            //读取本次从文件读取到buffer里面的数据
            buffer.flip();
            //计算下次文件读取的开始位置
            position = position + buffer.limit();
            while (buffer.hasRemaining()) {
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                //将本次从文件里面读取到的数据保存到总的读取结果byte数组totalByteList
                for (byte by : data) {
                    totalByteList.add(by);
                }
            }
            //重置临时读取结果ByteBuffer对象,以便下次循环使用
            buffer.clear();
        }

        //将读取结果byte数组totalByteList转换成字符串并打印出来
        byte[] bytes = new byte[totalByteList.size()];
        for (int i = 0; i < totalByteList.size(); i++) {
            bytes[i] = totalByteList.get(i);
        }
        String fileContent = new String(bytes);
        System.out.println("content :" + fileContent);
        ch.close();
    }


    //使用CompletionHandler异步写文件
    @Test
    public void test2() throws IOException, InterruptedException {
        //若文件p.txt不存在,则创建该文件
        Path path = Paths.get("/Users/liyebing/p.txt");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        //获得异步写入文件的fileChannel
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        //指定写入文件的开始位置
        long position = 0;
        //构造文件写入内容
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("你好,java Blocking I/O!".getBytes());
        buffer.flip();

        //异步写入操作
        final CountDownLatch latch = new CountDownLatch(1);
        fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            public void completed(Integer result, ByteBuffer attachment) {
                //写入操作成功完成
                latch.countDown();
            }

            public void failed(Throwable exc, ByteBuffer attachment) {
                //写入操作出错
                latch.countDown();
                exc.printStackTrace();
            }
        });

        //等待异步写入完成
        latch.await();
        //写入操作完成后打印信息到控制台
        System.out.println("Async Write File done");
    }


    //使用Future异步读文件
    @Test
    public void test3() throws IOException {
        //打开文件通道,获取异步Channel对象
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("/Users/liyebing/p.txt"), StandardOpenOption.READ);
        //读取文件临时ByteBuffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //文件读取位置
        long position = 0;
        //文件读取结果内容
        List<Byte> totalByteList = new ArrayList<Byte>();

        while (true) {
            //使用Future异步读取文件内容
            Future<Integer> operation = channel.read(buffer, position);
            //do nothing ,等待读取完成
            while (!operation.isDone()) ;

            buffer.flip();
            //若本次没读取到数据,说明已经读取完毕,直接跳出循环
            if (!buffer.hasRemaining()) {
                break;
            }

            //计算下次文件读取的开始位置
            position = position + buffer.limit();
            while (buffer.hasRemaining()) {
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                //将本次从文件里面读取到的数据保存到总的读取结果byte数组totalByteList
                for (byte by : data) {
                    totalByteList.add(by);
                }
            }
            //重置临时读取结果ByteBuffer对象,以便下次循环使用
            buffer.clear();
        }

        //将读取结果byte数组totalByteList转换成字符串并打印出来
        byte[] bytes = new byte[totalByteList.size()];
        for (int i = 0; i < totalByteList.size(); i++) {
            bytes[i] = totalByteList.get(i);
        }
        String fileContent = new String(bytes);
        System.out.println("content :" + fileContent);
        channel.close();
    }


    //使用Future异步写文件
    @Test
    public void test4() throws IOException {
        //若文件p.txt不存在,则创建该文件
        Path path = Paths.get("/Users/liyebing/p.txt");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        //获得文件异步写入fileChannel
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        //指定写入文件的开始位置
        long position = 0;

        //构造写入文件的内容
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("你好,java Blocking I/O!".getBytes());
        buffer.flip();

        //写入文件操作
        Future<Integer> operation = fileChannel.write(buffer, position);
        buffer.clear();

        //等待写入操作完成,关闭文件通道
        while (!operation.isDone()) ;
        fileChannel.close();

        //写入操作完成后打印信息到控制台
        System.out.println("Async Write File done");
    }

}
