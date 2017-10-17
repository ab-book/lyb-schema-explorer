package serializer.impl;

import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import serializer.ISerializer;
import thrift.User;

/**
 * @author liyebing created on 17/1/25.
 * @version $Id$
 */
public class ThriftSerializer implements ISerializer {


    public static void main(String[] args) throws TException {
        User user = new User();
        user.setEmail("kongxuan@163.com");
        user.setName("kongxuan");

        //序列化
        byte[] data = new ThriftSerializer().serialize(user);
        //反序列化
        User newUser = new ThriftSerializer().deserialize(data, User.class);
        System.out.println("email:" + newUser.getEmail() + " name:" + newUser.getName());


        data = new ThriftSerializer().serialize("test");
        String test= new ThriftSerializer().deserialize(data, String.class);
        System.out.println(test);
    }


    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof TBase)) {
                throw new UnsupportedOperationException("not supported obj type");
            }

            TSerializer serializer = new TSerializer(new TCompactProtocol.Factory());
            return serializer.serialize((TBase) obj);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }


    public <T> T deserialize(byte[] data, Class<T> cls) {
        try {
            if (!TBase.class.isAssignableFrom(cls)) {
                throw new UnsupportedOperationException("not supported obj type");
            }

            //TBinaryProtocol  TJSONProtocol  TCompactProtocol

            TBase o = (TBase) cls.newInstance();
            TDeserializer tDeserializer = new TDeserializer(new TCompactProtocol.Factory());
            tDeserializer.deserialize(o, data);
            return (T) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
