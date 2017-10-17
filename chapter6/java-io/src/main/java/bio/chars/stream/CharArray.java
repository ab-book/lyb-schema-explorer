package bio.chars.stream;

import org.junit.Test;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;

/**
 * @author liyebing created on 17/2/23.
 * @version $Id$
 */
public class CharArray {


    @Test
    public void test() throws IOException {

        //将字符串转换成字符数组
        String content = "你好!java Blocking I/O!";
        //将字符数组转换成字符输入流CharArrayReader
        CharArrayReader charReader = new CharArrayReader(content.toCharArray());

        //将字符输入流数据写入到字符输出流CharArrayWriter
        char[] chars = new char[1024];
        int size = 0;
        CharArrayWriter charWriter = new CharArrayWriter();
        while ((size = charReader.read(chars)) != -1) {
            charWriter.write(chars, 0, size);
        }

        //获取字符串并打印到控制台
        System.out.println(charWriter.toString());
        //获取字符数组并打印到控制台
        char[] charArray = charWriter.toCharArray();
        for (char c : charArray) {
            System.out.println(c);
        }
    }


}
