package nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author liyebing created on 17/2/16.
 * @version $Id$
 */
public class ChannelDemo {


    @Test
    public void test1() throws IOException {

        FileInputStream fin = new FileInputStream("src.txt");
        //从输入流中获取源文件src.txt的通道
        FileChannel finChannel = fin.getChannel();

        FileOutputStream fout = new FileOutputStream("target.txt");
        //从输出流获取目标文件target.txt的通道
        FileChannel foutChannel = fout.getChannel();

        //使用transferTo API 将文件src.txt内容写入target.txt
        finChannel.transferTo(0, finChannel.size(), foutChannel);

        //关闭文件流以及通道
        fin.close();
        finChannel.close();
        fout.close();
        foutChannel.close();
    }


    @Test
    public void test2() throws IOException {

        FileInputStream fin = new FileInputStream("src.txt");
        //从输入流中获取源文件src.txt的通道
        FileChannel finChannel = fin.getChannel();

        FileOutputStream fout = new FileOutputStream("target.txt");
        //从输出流获取目标文件target.txt的通道
        FileChannel foutChannel = fout.getChannel();

        //文件读取内容buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int bytesRead = finChannel.read(buf);
        //一次性可能读不完,所以需要循环读取
        while (bytesRead != -1) {
            //翻转buffer,为下面的读取做准备
            buf.flip();
            while (buf.hasRemaining()) {
                //将读取到的内容写入target.txt
                foutChannel.write(buf);
            }
            //复位buffer,以便再次复用buffer
            buf.clear();
            bytesRead = finChannel.read(buf);
        }

        //关闭文件流以及通道
        fin.close();
        finChannel.close();
        fout.close();
        foutChannel.close();
    }

}
