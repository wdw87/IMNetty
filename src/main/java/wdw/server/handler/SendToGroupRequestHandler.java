package wdw.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import wdw.protocal.request.SendToGroupRequestPacket;
import wdw.protocal.response.SendToGroupResponsePacket;
import wdw.session.Session;
import wdw.util.SessionUtil;

public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupRequestPacket sendToGroupRequestPacket) throws Exception {
        String gid = sendToGroupRequestPacket.getGid();
        String msg = sendToGroupRequestPacket.getMsg();
        String fromId = SessionUtil.getSession(channelHandlerContext.channel()).getUserId();
        String fromName = SessionUtil.getSession(channelHandlerContext.channel()).getUserName();

        SendToGroupResponsePacket packet = new SendToGroupResponsePacket();



        ChannelGroup chg = SessionUtil.getChannelGroup(gid);

        if(chg != null){
            packet.setFromId(fromId);
            packet.setFromName(fromName);
            packet.setMsg(msg);
            packet.setSuccess(true);
            chg.writeAndFlush(packet);
        }else{
            packet.setSuccess(true);
            packet.setReason("no such group");
            channelHandlerContext.channel().writeAndFlush(packet);
        }



    }
}
