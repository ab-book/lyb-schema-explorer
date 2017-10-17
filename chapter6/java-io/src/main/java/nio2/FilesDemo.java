package nio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Iterator;

/**
 * @author liyebing created on 17/3/1.
 * @version $Id$
 */
public class FilesDemo {

    public static void main(String[] args) throws IOException {

        //遍历linux根目录 / 下的子目录
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("/"));
        Iterator<Path> iterator = directoryStream.iterator();
        while (iterator.hasNext()) {
            Path path = iterator.next();
            System.out.println(path);
        }

        //递归创建文件目录
        Path path = Files.createDirectories(Paths.get("/Users/liyebing/test/test/"));
        System.out.println(path.getFileName());

        //创建文件test.txt
        Path file = Files.createFile(Paths.get("/Users/liyebing/test/test/test.txt"));

        //使用缓冲字符流
        Charset charset = Charset.forName("UTF-8");
        String text = "Hello,java NIO2!";
        try {
            BufferedWriter writer = Files.newBufferedWriter(file, charset, StandardOpenOption.APPEND);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
        }

        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(file, charset);
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
