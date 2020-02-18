package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.response.ListGroupResponsePacket;
import wdw.session.Session;

import java.util.List;

public class ListGroupResponseHandler extends SimpleChannelInboundHandler<ListGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupResponsePacket listGroupResponsePacket) throws Exception {
        boolean success = listGroupResponsePacket.isSuccess();
        if(success){
            List<Session> list = listGroupResponsePacket.getSessionList();
            System.out.println("group [" + listGroupResponsePacket.getGroupId() + "] has members [" + list + "]");
        }else{
            System.out.println("quit failed, reason :" + listGroupResponsePacket.getReason());
        }
    }
}
