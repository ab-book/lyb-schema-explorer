package serializer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import model.User;
import serializer.ISerializer;

/**
 * @author liyebing created on 17/4/30.
 * @version $Id$
 */
public class JSON2Serializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T) JSON.parseObject(new String(data), clazz);
    }

    public static void main(String[] args) {
        User u = new User();
        u.setEmail("liyebing@163.com");
        u.setName("kongxuan");

        JSON2Serializer serializer = new JSON2Serializer();
        byte[] bytes = serializer.serialize(u);
        User u1 = serializer.deserialize(bytes, User.class);
        System.out.println(u1);
    }
}
