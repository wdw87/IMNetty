package wdw.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.PacketCodeC;
import wdw.protocal.request.LoginRequestPacket;
import wdw.protocal.response.LoginResponsePacket;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": received login access...");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            //校验成功
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setReason("login success");
            System.out.println(new Date() + " :");
            System.out.println("user logined:");
            System.out.println("username: " + loginRequestPacket.getUsername());
        } else {
            //校验失败
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("password error");
        }

        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
