package bio.bytes.stream;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author liyebing created on 17/2/23.
 * @version $Id$
 */
public class PrintsStream {

    @Test
    public void test() throws IOException {

        File file = new File("/Users/liyebing/p.txt");
        PrintStream printStream = new PrintStream(file);
        printStream.println("你好,java Blocking I/O!");
        printStream.close();

    }

}
