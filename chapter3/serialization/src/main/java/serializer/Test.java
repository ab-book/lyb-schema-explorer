package serializer;

import serializer.impl.DefaultJavaSerializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author liyebing created on 17/4/30.
 * @version $Id$
 */
public class Test implements Serializable {

    private transient Integer age = 28;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(age);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        age = in.readInt();
    }


    public static void main(String[] args) {
        byte[] bytes =new DefaultJavaSerializer().serialize(new Test());
        Test t = new DefaultJavaSerializer().deserialize(bytes,Test.class);
        System.out.println(t);
    }
}
