package netty.custom.v2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liyebing created on 17/1/19.
 * @version $Id$
 */
public class CustomV2Encoder extends MessageToByteEncoder {


    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        //使用hessian序列化对象
        byte[] data = HessianSerializer.serialize(in);
        //先写入消息的长度作为消息头
        out.writeInt(data.length);
        //最后写入消息体字节数组
        out.writeBytes(data);
    }
}
