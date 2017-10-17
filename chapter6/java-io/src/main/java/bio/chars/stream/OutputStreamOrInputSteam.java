package bio.chars.stream;

import org.junit.Test;

import java.io.*;

/**
 * @author liyebing created on 17/2/24.
 * @version $Id$
 */
public class OutputStreamOrInputSteam {

    @Test
    public void test() throws IOException {
        //创建文件字节输入流
        FileInputStream inputStream = new FileInputStream("src.txt");
        //利用桥梁InputStreamReader将文件字节输入流inputStream转换成字符输入流inputStreamReader
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        //利用BufferedReader包装字符输入流inputStreamReader提高性能
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        //创建文件字节输出流
        FileOutputStream outputStream = new FileOutputStream("target.txt");
        //利用桥梁outputStreamWriter将文件字节输出流outputStream转换成字符输出流outputStreamWriter
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        //利用BufferedWriter包装字符输出流inputStreamReader提高性能
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        //将文件src.txt文本内容写入到target.txt
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();

        bufferedReader.close();
        bufferedWriter.close();
        inputStream.close();
        outputStream.close();
    }

}
