package wdw.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器推送消息");

        ByteBuf pushMsg = getByteBuf(ctx);
        ctx.channel().writeAndFlush(pushMsg);
    }

    /*
        该方法在服务器收到数据后调用
         */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println(new Date() + "->服务器收到消息： " + ((ByteBuf) msg).toString(Charset.forName("utf-8")));
        System.out.println("服务器发送消息");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf buf = ctx.alloc().buffer();

        byte[] bytes = "我是服务器！！！".getBytes(Charset.forName("utf-8"));

        buf.writeBytes(bytes);
        return buf;
    }
}
