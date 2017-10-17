package serializer.impl;

import com.google.protobuf.GeneratedMessageV3;
import model.AddressBookProtos;
import org.apache.commons.lang3.reflect.MethodUtils;
import serializer.ISerializer;

import java.util.Arrays;

/**
 * @author liyebing created on 17/1/25.
 * @version $Id$
 */
public class ProtoBufSerializer implements ISerializer {


    public static void main(String[] args) throws Exception {
        //构建一个Person对象
        AddressBookProtos.Person person = AddressBookProtos.Person
                .newBuilder()
                .setEmail("kongxuan@163.com")
                .setId(10000)
                .setName("kongxuan")
                .addPhone(
                        AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("13300000000")
                                .setType(AddressBookProtos.Person.PhoneType.HOME).build())
                .build();


        //序列化
        System.out.println(person.toByteString());
        System.out.println(Arrays.toString(person.toByteArray()));

        // 反序列化方法一
        AddressBookProtos.Person newPerson = AddressBookProtos.Person.parseFrom(person.toByteString());
        System.out.println(newPerson);
        // 反序列化方法二
        newPerson = AddressBookProtos.Person.parseFrom(person.toByteArray());
        System.out.println(newPerson);

        // 向地址簿添加两条Person信息
        AddressBookProtos.AddressBook.Builder books = AddressBookProtos.AddressBook.newBuilder();
        books.addPerson(person);
        books.addPerson(AddressBookProtos.Person.newBuilder(person).setEmail("kongxuan@163.com").build());
        System.out.println(books.build());

        System.out.println("------------------------------------------------");
        byte[] data = new ProtoBufSerializer().serialize(person);
        AddressBookProtos.Person personCopy = new ProtoBufSerializer().deserialize(data, AddressBookProtos.Person.class);
        System.out.println(personCopy);
    }

    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof GeneratedMessageV3)) {
                throw new UnsupportedOperationException("not supported obj type");
            }

            return (byte[]) MethodUtils.invokeMethod(obj, "toByteArray");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] data, Class<T> cls) {
        try {
            if (!GeneratedMessageV3.class.isAssignableFrom(cls)) {
                throw new UnsupportedOperationException("not supported obj type");
            }

            Object o = MethodUtils.invokeStaticMethod(cls, "getDefaultInstance");
            return (T) MethodUtils.invokeMethod(o, "parseFrom", new Object[]{data});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
