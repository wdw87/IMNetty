package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.response.LogoutResponsePacket;
import wdw.util.SessionUtil;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) throws Exception {
        //SessionUtil.unBindChannel(ctx.channel());
        System.out.println("bye!");

    }
}
