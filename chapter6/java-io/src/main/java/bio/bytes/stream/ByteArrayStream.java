package bio.bytes.stream;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author liyebing created on 17/2/22.
 * @version $Id$
 */
public class ByteArrayStream {

    @Test
    public void test1() throws IOException {

        //将字符串转换成字节数组
        String content = "你好,java Blocking I/O!";
        byte[] inputBytes = content.getBytes(Charset.forName("utf-8"));

        //将字节数组转换成字节输入流ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBytes);

        //将字节输入流数据写入到字节输出流ByteArrayOutputStream
        byte[] bytes = new byte[1024];
        int size = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((size = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, size);
        }

        //将字节输出流转换成字符串打印到控制台
        System.out.println(outputStream.toString("utf-8"));

    }

}
