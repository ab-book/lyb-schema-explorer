package bio.chars.stream;

import org.junit.Test;

import java.io.*;

/**
 * @author liyebing created on 17/2/24.
 * @version $Id$
 */
public class BufferedStream {

    @Test
    public void test() throws IOException {
        FileReader fr = new FileReader("src.txt");
        FileWriter fw = new FileWriter("target.txt");
        BufferedReader bufReader = new BufferedReader(fr);
        BufferedWriter bufWriter = new BufferedWriter(fw);

        //利用BufferedReader/BufferedWriter实现逐行读写,提高I/O性能
        String line = null;
        while ((line = bufReader.readLine()) != null) {
            bufWriter.write(line);
            bufWriter.newLine();
        }
        bufWriter.flush();
        bufReader.close();
        bufWriter.close();
    }
}
