package wdw.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import wdw.util.LoginUtil;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!LoginUtil.hadLogin(ctx.channel())){
            ctx.channel().close();
        }else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(LoginUtil.hadLogin(ctx.channel())){
            System.out.println("登录验证完成，无需再次验证，AuthHandler已被移除");
        }else{
            System.out.println("验证失败,关闭连接");
        }
    }
}
