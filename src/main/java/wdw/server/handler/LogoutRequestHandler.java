package wdw.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.request.LogoutRequestPacket;
import wdw.protocal.response.LogoutResponsePacket;
import wdw.util.SessionUtil;

import java.util.Date;

public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket logoutRequestPacket) throws Exception {
        String user = SessionUtil.getSession(ctx.channel()).getUserName();
        String id = SessionUtil.getSession(ctx.channel()).getUserId();
        System.out.println(new Date() + ": user [" + user + "-" + id + "] logout");
        SessionUtil.unBindChannel(ctx.channel());
        LogoutResponsePacket packet = new LogoutResponsePacket();
        ctx.channel().writeAndFlush(packet);
    }
}
