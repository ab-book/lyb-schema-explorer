package netty.custom.v1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liyebing created on 17/1/19.
 * @version $Id$
 */
public class CustomV1Encoder extends MessageToByteEncoder {


    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        //使用hessian序列化对象
        byte[] data = HessianSerializer.serialize(in);
        out.writeBytes(data);
    }
}
