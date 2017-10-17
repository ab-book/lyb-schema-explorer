package serializer.impl;

import avro.User;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import serializer.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author liyebing created on 17/1/26.
 * @version $Id$
 */
public class AvroSerializer implements ISerializer {


    public static void main(String[] args) throws IOException {
        User userAvro = new User();
        userAvro.setAge(28);
        userAvro.setEmail("kongxuan@163.com");
        userAvro.setName("kongxuan");

        //1.先自动生成代码,再序列化方式
        //1.1 序列化
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(outputStream, null);
        userDatumWriter.write(userAvro, binaryEncoder);
        byte[] data = outputStream.toByteArray();

        //1.2 反序列化
        DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
        BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(new ByteArrayInputStream(data), null);
        User userAvroCopy = userDatumReader.read(new User(), binaryDecoder);
        System.out.println("age:" + userAvroCopy.getAge() + " email:" + userAvroCopy.getEmail() + " name:" + userAvroCopy.getName());

        System.out.println("-------------------------------------------------------------------------------------------------------------");


        //2.直接根据avsc文件进行序列化的操作方式
        Schema schema = new Schema.Parser().parse(new File("src/main/avro/user.avsc"));
        GenericRecord userAvro2 = new GenericData.Record(schema);
        userAvro2.put("age", 18);
        userAvro2.put("email", "liyebing@163.com");
        userAvro2.put("name", "liyebing");

        //2.1 序列化
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder2 = EncoderFactory.get().directBinaryEncoder(outputStream2, null);
        datumWriter.write(userAvro2, binaryEncoder2);
        byte[] data2 = outputStream2.toByteArray();


        //2.2 反序列化
        DatumReader<User> userDatumReader2 = new SpecificDatumReader<User>(User.class);
        BinaryDecoder binaryDecoder2 = DecoderFactory.get().directBinaryDecoder(new ByteArrayInputStream(data2), null);
        User userAvroCopy2 = userDatumReader2.read(new User(), binaryDecoder2);
        System.out.println("age:" + userAvroCopy2.getAge() + " email:" + userAvroCopy2.getEmail() + " name:" + userAvroCopy2.getName());

        System.out.println("----------------------------------------------------------");

        //3.以文件的形式将序列化
        //3.1 序列化
        File file = new File("users.avro");
        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
        dataFileWriter.create(userAvro.getSchema(), file);
        dataFileWriter.append(userAvro);
        dataFileWriter.append(userAvroCopy2);
        dataFileWriter.close();

        //3.2 从文件中反序列化
        DataFileReader<User> dataFileReader = new DataFileReader<User>(file, userDatumReader);
        User user = null;
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next(user);
            System.out.println("age:" + user.getAge() + " email:" + user.getEmail() + " name:" + user.getName());
        }

        System.out.println("--------------------------------------------------------begin---------");
        byte[] data_1 = new AvroSerializer().serialize(userAvro);
        User user1 = new AvroSerializer().deserialize(data_1, User.class);
        System.out.println("age:" + user1.getAge() + " email:" + user1.getEmail() + " name:" + user1.getName());
    }


    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof SpecificRecordBase)) {
                throw new UnsupportedOperationException("not supported obj type");
            }

            DatumWriter userDatumWriter = new SpecificDatumWriter(obj.getClass());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(outputStream, null);
            userDatumWriter.write(obj, binaryEncoder);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            if (!SpecificRecordBase.class.isAssignableFrom(clazz)) {
                throw new UnsupportedOperationException("not supported clazz type");
            }

            DatumReader userDatumReader = new SpecificDatumReader(clazz);
            BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(new ByteArrayInputStream(data), null);
            return (T) userDatumReader.read(clazz.newInstance(), binaryDecoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
