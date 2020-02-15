package wdw.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import wdw.protocal.Packet;
import wdw.protocal.PacketCodeC;
import wdw.protocal.request.LoginRequestPacket;
import wdw.protocal.response.LoginResponsePacket;
import wdw.protocal.response.MessageResponsePacket;
import wdw.util.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setUesrId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("wdw");
        loginRequestPacket.setPassword("3346535");

        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        ctx.channel().writeAndFlush(buffer);


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf responseByteBuf = (ByteBuf)msg;

        Packet packet = PacketCodeC.INSTANCE.decode(responseByteBuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket responsePacket = (LoginResponsePacket)packet;

            boolean success = responsePacket.isSuccess();
            String reason = responsePacket.getReason();
            if(success){
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": login success ");
            }else{
                System.out.println(new Date() + ": login failed, " + reason);
            }
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket responsePacket = (MessageResponsePacket)packet;

            String mesg = responsePacket.getMessage();
            System.out.println(new Date() + ": server received :" + "[" + mesg + "]");
        }

    }
}
