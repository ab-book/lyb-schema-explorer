package bio.bytes.stream;

import org.junit.Test;

import java.io.*;

/**
 * @author liyebing created on 17/2/23.
 * @version $Id$
 */
public class BufferedStream {

    @Test
    public void test() throws IOException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;


        //定义源文件与目标文件
        File srcFile = new File("/Users/liyebing/src.txt");
        File targetFile = new File("/Users/liyebing/target.txt");

        try {
            //实例化文件输入流与文件输出流
            inputStream = new FileInputStream(srcFile);
            BufferedInputStream bufferedInput = new BufferedInputStream(inputStream);

            outputStream = new FileOutputStream(targetFile);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(outputStream);

            //通过缓冲输入流读取源文件内容,并写入到缓冲输出流,最终写入文件
            byte[] buff = new byte[1024];
            int byt;
            while ((byt = bufferedInput.read(buff, 0, buff.length)) != -1) {
                bufferedOutput.write(buff, 0, byt);
            }
            bufferedOutput.flush();

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }


    }

}
