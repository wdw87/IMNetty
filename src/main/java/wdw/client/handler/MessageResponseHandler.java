package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.response.MessageResponsePacket;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromId = messageResponsePacket.getFromId();
        String fromUser = messageResponsePacket.getFromName();

        String mesg = messageResponsePacket.getMessage();

        System.out.println(new Date() + ": " + fromId + " : " + fromUser);
        System.out.println("\t" + mesg);
    }
}
