package wdw.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import wdw.util.SessionUtil;
// 1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    // 2. 构造单例
    public static final AuthHandler INSTANCE = new AuthHandler();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!SessionUtil.hadLogin(ctx.channel())){
            ctx.channel().close();
        }else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(SessionUtil.hadLogin(ctx.channel())){
            System.out.println("登录验证完成，无需再次验证，AuthHandler已被移除");
        }else{
            System.out.println("验证失败,关闭连接");
        }
    }
}
