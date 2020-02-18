package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.response.LoginResponsePacket;
import wdw.util.SessionUtil;

import java.util.Date;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(new Date() + ": hello, input command 'login + [name]' to login");
    }

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
