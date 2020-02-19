package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.response.SendToGroupResponsePacket;

import java.util.Date;

public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupResponsePacket sendToGroupResponsePacket) throws Exception {
        boolean success = sendToGroupResponsePacket.isSuccess();

        if(success){
            String fName = sendToGroupResponsePacket.getFromName();
            String msg = sendToGroupResponsePacket.getMsg();
            System.out.println(new Date() + ": " + fName + ":");
            System.out.println(msg);
        }else{
            String reason = sendToGroupResponsePacket.getReason();
            System.out.println("failed, reason :" + reason);
        }
    }
}
