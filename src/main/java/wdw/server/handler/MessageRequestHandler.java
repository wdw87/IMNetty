package wdw.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.PacketCodeC;
import wdw.protocal.request.MessageRequestPacket;
import wdw.protocal.response.MessageResponsePacket;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        String message = messageRequestPacket.getMessage();
        System.out.println(new Date() + ": receive from client: " + "[" + message + "]");

        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage("received : " + message);

        channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
