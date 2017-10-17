package bio.bytes.stream;

import org.junit.Test;

import java.io.*;

/**
 * @author liyebing created on 17/2/23.
 * @version $Id$
 */
public class DataStream {


    @Test
    public void test() throws IOException {

        String fileName = "/Users/liyebing/data.txt";

        //将java原生类型数据通过DataOutputStream写入文件
        FileOutputStream fout = new FileOutputStream(fileName);
        DataOutputStream dos = new DataOutputStream(fout);

        dos.writeInt(2017);
        dos.writeUTF("你好,java Blocking I/O!");
        dos.writeBoolean(true);

        dos.close();
        fout.close();

        //使用DataInputStream从文件中按照写入顺序读取java原生类型数据
        FileInputStream fin = new FileInputStream(fileName);
        DataInputStream dis = new DataInputStream(fin);

        System.out.println(dis.readInt());
        System.out.println(dis.readUTF());
        System.out.println(dis.readBoolean());

        dis.close();
        fin.close();
    }


}
