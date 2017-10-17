package netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liyebing created on 16/2/20.
 * @version $Id$
 */
public class EchoServerHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收客户端发来的数据,使用buf.readableBytes()获取数据大小,并转换成byte数组
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        //将byte数组转成字符串,在控制台打印输出
        String body = new String(req, "UTF-8");
        System.out.println("receive data from client:" + body);

        //将接收到的客户端发来的数据回写到客户端
        ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
        ctx.write(resp);
    }


    //发生异常,关闭链路
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //将发送缓冲区中的消息全部写入到SocketChannel中
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
