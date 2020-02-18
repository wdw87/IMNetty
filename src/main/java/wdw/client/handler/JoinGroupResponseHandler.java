package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.response.JoinGroupResponsePacket;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        boolean success = joinGroupResponsePacket.isSuccess();
        if(success){
            System.out.println("join success");
        }else{
            System.out.println("join failed, reason :" + joinGroupResponsePacket.getReason());
        }
    }
}
