package wdw.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import wdw.protocal.request.JoinGroupRequestPacket;
import wdw.protocal.response.JoinGroupResponsePacket;
import wdw.util.SessionUtil;

import java.util.Date;

@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        String groupId = joinGroupRequestPacket.getGroupId();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        JoinGroupResponsePacket packet = new JoinGroupResponsePacket();
        Channel ch = channelHandlerContext.channel();
        String userId = SessionUtil.getSession(ch).getUserId();

        System.out.println(new Date() + ": user [" + userId + "] attempt join group [" + groupId + "]...");

        if(channelGroup != null){
            channelGroup.add(ch);
            System.out.println("success");
            packet.setSuccess(true);
        }else{

            System.out.println("failed... no such group");
            packet.setReason("no such group");
            packet.setSuccess(false);

        }

        ch.writeAndFlush(packet);
    }
}
