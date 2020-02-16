package wdw.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.request.LoginRequestPacket;
import wdw.protocal.response.LoginResponsePacket;
import wdw.session.Session;
import wdw.util.SessionUtil;

import java.util.Date;
import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": received login access...");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {
            //校验成功
            String userId = getRandomId();
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setReason("login success");
            loginResponsePacket.setUserId(userId);

            SessionUtil.bingChannel(new Session(loginRequestPacket.getUsername(), userId), channelHandlerContext.channel());

            System.out.println(new Date() + ": user [" + loginRequestPacket.getUsername() + "] logined");
            SessionUtil.markAsLogin(channelHandlerContext.channel());
        } else {
            //校验失败
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("password error");
        }

        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    private String getRandomId(){
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String user = SessionUtil.getSession(ctx.channel()).toString();
        SessionUtil.unBindChannel(ctx.channel());
        System.out.println(new Date() + ": user " + user + " disconnected !");
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
