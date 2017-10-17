package serializer.impl;

import model.User;
import serializer.ISerializer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author liyebing created on 17/4/30.
 * @version $Id$
 */
public class XML2Serializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder xe = new XMLEncoder(out, "utf-8", true, 0);
        xe.writeObject(obj);
        xe.close();
        return out.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        XMLDecoder xd = new XMLDecoder(new ByteArrayInputStream(data));
        Object obj = xd.readObject();
        xd.close();
        return (T) obj;
    }


    public static void main(String[] args) {
        User u = new User();
        u.setEmail("liyebing@163.com");
        u.setName("kongxuan");

        XML2Serializer serializer=new XML2Serializer();
        byte[] bytes= serializer.serialize(u);
        User u1= serializer.deserialize(bytes,User.class);
        System.out.println(u1);
    }
}
