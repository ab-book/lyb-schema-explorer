package bio.bytes.stream;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author liyebing created on 17/2/23.
 * @version $Id$
 */
public class FileStream {

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
            outputStream = new FileOutputStream(targetFile);

            //通过文件输入流读取源文件内容,并写入到目标文件
            int byt;
            while ((byt = inputStream.read()) != -1) {
                outputStream.write(byt);
            }

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
