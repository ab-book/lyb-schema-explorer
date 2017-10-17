package netty.javaserialize;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author liyebing created on 17/3/22.
 * @version $Id$
 */
public class JavaSerializeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //channel建立之后,向服务端发送消息,需要注意的是这里写入的消息是完整的UserInfo对象
        //因为后续会被ObjectEncoder进行编码处理
        UserInfo user = UserInfo.newBuilder()
                .name("liyebing")
                .userId(10000)
                .email("liyebing@163.com")
                .mobile("153****0976")
                .remark("remark info").build();

        ctx.writeAndFlush(user);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
