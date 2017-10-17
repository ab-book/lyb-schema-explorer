package netty.custom.v2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author liyebing created on 17/1/19.
 * @version $Id$
 */
public class CustomV2Decoder extends ByteToMessageDecoder {

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //读取消息头,整个消息的长度字段
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        //读取字节数组,直到读取的字节数组长度等于dataLength
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        //将字节数组使用Hession反序列化为对象
        Object obj = HessianSerializer.deserialize(data);
        out.add(obj);
    }

}
