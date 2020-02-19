package wdw.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.PacketCodeC;
import wdw.protocal.request.MessageRequestPacket;
import wdw.protocal.response.MessageResponsePacket;
import wdw.session.Session;
import wdw.util.SessionUtil;

import java.util.Date;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {

        String toId = messageRequestPacket.getToId();
        String message = messageRequestPacket.getMessage();

        //拿到发消息方的Session
        Session fromSession = SessionUtil.getSession(channelHandlerContext.channel());

        MessageResponsePacket responsePacket = new MessageResponsePacket();

        responsePacket.setFromId(fromSession.getUserId());
        responsePacket.setFromName(fromSession.getUserName());
        responsePacket.setMessage(message);

        Channel toChannel = SessionUtil.getChannel(toId);
        if(toChannel != null){
            toChannel.writeAndFlush(responsePacket);

        }else{
            System.out.println("无此用户");
        }

        System.out.println(new Date() + ": receive from client: " + "[" + message + "]");


    }
}
