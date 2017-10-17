package bio;

import common.User;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author liyebing created on 17/2/14.
 * @version $Id$
 */
public class StreamsTest {

    /**
     * 演示字节流以及对象流的使用
     *
     * @throws Exception
     */
    @Test
    public void testByteArrayStream() throws Exception {
        User user = new User();
        user.setName("liyebing");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
        byte[] bytes = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        User newUser = (User) objectInputStream.readObject();
        System.out.println(newUser.getName());
    }






}
