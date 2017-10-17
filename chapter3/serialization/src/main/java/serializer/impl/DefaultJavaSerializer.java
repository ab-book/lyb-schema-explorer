package serializer.impl;

import model.User;
import serializer.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * java默认序列化
 *
 * @author liyebing created on 17/1/21.
 * @version $Id$
 */
public class DefaultJavaSerializer implements ISerializer {


    @SuppressWarnings("unchecked")
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);

            objectOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }


    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] data, Class<T> clazz) {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        User u = new User();
        u.setEmail("liyebing@163.com");
        u.setName("kongxuan");

        User u1 = new User();
        u1.setEmail("liyebing@162.com");
        u1.setName("kongxuan11");

        List<User> userList = new ArrayList<User>();
        Map<String, User> userMap = new HashMap<String, User>();
        userList.add(u1);
        userMap.put("a", u1);

        u.setUserList(userList);
        u.setUserMap(userMap);


        byte[] userByte = new DefaultJavaSerializer().serialize(u);
        User user = new DefaultJavaSerializer().deserialize(userByte, User.class);
        System.out.println(user.getEmail() + " : " + user.getName() + " : " + new String(new JSONSerializer().serialize(u.getUserList())) + " : " + new String(new JSONSerializer().serialize(u.getUserMap())));
    }

}
