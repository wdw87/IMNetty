package wdw.client.console;

import io.netty.channel.Channel;
import wdw.protocal.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel ch) {
        System.out.println("please input message: ");
        String toId = sc.next();
        String message = sc.next();
        MessageRequestPacket packet = new MessageRequestPacket();
        packet.setToId(toId);
        packet.setMessage(message);

        ch.writeAndFlush(packet);
    }
}
