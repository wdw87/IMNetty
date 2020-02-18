package wdw.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import wdw.protocal.request.CreateGroupRequestPacket;
import wdw.protocal.response.CreateGroupResponsePacket;
import wdw.util.IDUtil;
import wdw.util.SessionUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {

        List<String> userList = createGroupRequestPacket.getUserList();
        List<String> nameList = new ArrayList<>();

        ChannelGroup channelGroup = new DefaultChannelGroup(channelHandlerContext.executor());

        for(String user : userList){
            Channel ch = SessionUtil.getChannel(user);
            if(ch != null){
                channelGroup.add(ch);
                nameList.add(SessionUtil.getSession(ch).getUserName());
            }
        }

        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        responsePacket.setUserNameList(nameList);

        channelGroup.writeAndFlush(responsePacket);


        SessionUtil.bindChannelGroup(groupId, channelGroup);

        System.out.println(new Date() + "成功创建群组, id为 [" + responsePacket.getGroupId() + "]");
        System.out.println("群成员有:" + responsePacket.getUserNameList());

    }
}
