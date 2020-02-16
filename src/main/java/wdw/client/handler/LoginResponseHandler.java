package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.request.LoginRequestPacket;
import wdw.protocal.response.LoginResponsePacket;
import wdw.util.SessionUtil;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//
//        loginRequestPacket.setUesrId(UUID.randomUUID().toString());
//        loginRequestPacket.setUsername("wdw");
//        loginRequestPacket.setPassword("3346535");
//
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {

        boolean success = loginResponsePacket.isSuccess();
        String reason = loginResponsePacket.getReason();
        if(success){
            System.out.println(new Date() + ": login success ," + "user id :" + loginResponsePacket.getUserId());
            SessionUtil.markAsLogin(channelHandlerContext.channel());
        }else{
            System.out.println(new Date() + ": login failed, " + reason);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("登录失败，连接被关闭");
    }
}
