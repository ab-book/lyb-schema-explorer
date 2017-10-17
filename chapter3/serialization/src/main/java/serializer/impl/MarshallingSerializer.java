package serializer.impl;

import model.User;
import org.jboss.marshalling.*;
import serializer.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author liyebing created on 17/3/31.
 * @version $Id$
 */
public class MarshallingSerializer implements ISerializer {

    final static MarshallingConfiguration configuration = new MarshallingConfiguration();
    //获取序列化工厂对象,参数serial标识创建的是java序列化工厂对象
    final static MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

    static {
        configuration.setVersion(5);
    }


    public byte[] serialize(Object obj) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
            marshaller.start(Marshalling.createByteOutput(byteArrayOutputStream));
            marshaller.writeObject(obj);
            marshaller.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }


    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            final Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);
            unmarshaller.start(Marshalling.createByteInput(byteArrayInputStream));
            Object object = unmarshaller.readObject();
            unmarshaller.finish();
            return (T) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        User u = new User();
        u.setEmail("liyebing@163.com");
        u.setName("kongxuan");

        byte[] userByte = new MarshallingSerializer().serialize(u);
        User user = new MarshallingSerializer().deserialize(userByte, User.class);
        System.out.println(new String(new JSONSerializer().serialize(user)));
    }
}