package netty.custom.v1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author liyebing created on 17/1/19.
 * @version $Id$
 */
public class CustomV1Decoder extends ByteToMessageDecoder {

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //读取字节数组
        int dataLength = in.readableBytes();
        if (dataLength <= 0) {
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        //将字节数组使用Hession反序列化为对象
        Object obj = HessianSerializer.deserialize(data);
        out.add(obj);
    }

}
