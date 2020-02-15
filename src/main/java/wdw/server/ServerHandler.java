package wdw.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import wdw.protocal.Packet;
import wdw.protocal.PacketCodeC;
import wdw.protocal.request.LoginRequestPacket;
import wdw.protocal.request.MessageRequestPacket;
import wdw.protocal.response.LoginResponsePacket;
import wdw.protocal.response.MessageResponsePacket;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(loginRequestPacket.getVersion());
            if (valid(loginRequestPacket)) {
                //校验成功
                loginResponsePacket.setSuccess(true);
                loginResponsePacket.setReason("login success");
                System.out.println(new Date() + " :");
                System.out.println("user logined:");
                System.out.println("username: " + loginRequestPacket.getUsername());
            } else {
                //校验失败
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("password error");
            }
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);

            ctx.writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            String message = requestPacket.getMessage();
            System.out.println(new Date() + ": receive from client: " + "[" + message + "]");

            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setMessage("received : " + message);
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.writeAndFlush(byteBuf);

        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
