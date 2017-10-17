package netty.custom.v2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liyebing created on 17/3/21.
 * @version $Id$
 */
public class CustomServerHandler extends ChannelInboundHandlerAdapter {

    private static AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收客户端发送过来的消息,其中经过前面解码工具
        //的处理,将字节码消息自动转换成了UserInfo对象
        UserInfo req = (UserInfo) msg;
        System.out.println("received from client:" + req.toString() + " counter :" + counter.incrementAndGet());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
