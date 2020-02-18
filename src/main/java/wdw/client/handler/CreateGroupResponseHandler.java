package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.response.CreateGroupResponsePacket;

import java.util.Date;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        System.out.println(new Date() + "成功创建群组, id为 [" + createGroupResponsePacket.getGroupId() + "]");
        System.out.println("群成员有:" + createGroupResponsePacket.getUserNameList());
    }
}
