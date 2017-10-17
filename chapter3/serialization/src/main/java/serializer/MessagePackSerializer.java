//package serializer;
//
//import model.User;
//import org.msgpack.MessagePack;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author liyebing created on 17/4/1.
// * @version $Id$
// */
//public class MessagePackSerializer {
//
//    private MessagePackSerializer() {
//    }
//
//
//    public static byte[] serialize(Object obj) {
//        try {
//            MessagePack messagePack = new MessagePack();
//            return messagePack.write(obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public static <T> T deserialize(byte[] data, Class<T> clazz) {
//        try {
//            MessagePack messagePack = new MessagePack();
//            return (T) messagePack.read(data, clazz);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//        User u = new User();
//        u.setEmail("liyebing@163.com");
//        u.setName("kongxuan");
//
//        User u1 = new User();
//        u1.setEmail("liyebing@162.com");
//        u1.setName("kongxuan11");
//
//        List<User> userList = new ArrayList<User>();
//        Map<String, User> userMap = new HashMap<String, User>();
//        userList.add(u1);
//        userMap.put("a", u1);
//
//        u.setUserList(userList);
//        u.setUserMap(userMap);
//
//
//        byte[] userByte = MessagePackSerializer.serialize(u);
//        User user = MessagePackSerializer.deserialize(userByte, User.class);
//        System.out.println(user.getEmail() + " : " + user.getName() + " : " + new String(JSONSerializer.serialize(u.getUserList())) + " : " + new String(JSONSerializer.serialize(u.getUserMap())));
//    }
//}
