package serializer.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import model.User;
import serializer.ISerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liyebing created on 17/1/21.
 * @version $Id$
 */
public class XmlSerializer implements ISerializer {

    private static final XStream xStream = new XStream(new DomDriver());


    @SuppressWarnings("unchecked")
    public <T> byte[] serialize(T obj) {
        return xStream.toXML(obj).getBytes();
    }


    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String xml = new String(data);
        return (T) xStream.fromXML(xml);
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

        byte[] userByte = new XmlSerializer().serialize(u);
        User user = new XmlSerializer().deserialize(userByte, User.class);
        System.out.println(user.getEmail() + " : " + user.getName() + " : " + new String(new JSONSerializer().serialize(u.getUserList())) + " : " + new String(new JSONSerializer().serialize(u.getUserMap())));
    }
}
