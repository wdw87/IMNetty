package wdw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wdw.protocal.response.QuitGroupResponsePacket;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        boolean success = quitGroupResponsePacket.isSuccess();
        if(success){
            System.out.println("quit success");
        }else{
            System.out.println("quit failed, reason :" + quitGroupResponsePacket.getReason());
        }
    }
}
