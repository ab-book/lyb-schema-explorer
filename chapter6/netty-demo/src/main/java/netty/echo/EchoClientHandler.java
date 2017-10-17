package netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

/**
 * @author liyebing created on 16/2/20.
 * @version $Id$
 */
public class EchoClientHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = Logger.getLogger(EchoClientHandler.class.getName());

    //服务端响应请求返回数据的时候会自动调用该方法,我们通过实现该方法来接收服务端返回的数据,并实现客户端调用的业务逻辑
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取服务端返回的数据buf
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        //将服务端返回的byte数组转换成字符串,并打印到控制台
        String body = new String(req, "UTF-8");
        System.out.println("receive data from server :" + body);
    }

    //发生异常,关闭链路
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected exception from downstream :" + cause.getMessage());
        ctx.close();
    }


}
