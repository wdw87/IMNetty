package wdw.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import wdw.protocal.request.QuitGroupRequestPacket;
import wdw.protocal.response.QuitGroupResponsePacket;
import wdw.util.SessionUtil;

import java.util.Date;

@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        String groupId = quitGroupRequestPacket.getGroupId();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        QuitGroupResponsePacket packet = new QuitGroupResponsePacket();
        Channel ch = channelHandlerContext.channel();
        String userId = SessionUtil.getSession(ch).getUserId();

        System.out.println(new Date() + ": user [" + userId + "] attempt quit group [" + groupId + "]...");

        if(channelGroup != null){
            if(channelGroup.contains(ch)){
                channelGroup.remove(ch);
                System.out.println("success");
                packet.setSuccess(true);
            }else{
                System.out.println("failed... no such member in group");
                packet.setReason("no such member in group");
                packet.setSuccess(false);
            }
        }else{
            System.out.println("failed... no such group");
            packet.setReason("no such group");
            packet.setSuccess(false);

        }

        ch.writeAndFlush(packet);
    }
}
