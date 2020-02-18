package wdw.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import wdw.protocal.request.ListGroupRequestPacket;
import wdw.protocal.response.ListGroupResponsePacket;
import wdw.session.Session;
import wdw.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class ListGroupRequestHandler extends SimpleChannelInboundHandler<ListGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupRequestPacket listGroupRequestPacket) throws Exception {

        String groupId = listGroupRequestPacket.getGroupId();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        List<Session> list = new ArrayList<>();

        ListGroupResponsePacket packet = new ListGroupResponsePacket();

        packet.setGroupId(groupId);

        if(channelGroup != null){
            for(Channel ch : channelGroup){
                list.add(SessionUtil.getSession(ch));
            }
            packet.setSessionList(list);
            packet.setSuccess(true);
        }else{
            packet.setSuccess(false);
            packet.setReason("no such group");
        }
        channelHandlerContext.channel().writeAndFlush(packet);
    }
}
