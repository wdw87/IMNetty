package wdw.client.console;

import io.netty.channel.Channel;
import wdw.protocal.request.QuitGroupRequestPacket;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel ch) {
        System.out.println("please input group ID to quit:");
        String groupId = sc.next().trim();

        QuitGroupRequestPacket packet = new QuitGroupRequestPacket();
        packet.setGroupId(groupId);

        ch.writeAndFlush(packet);
    }
}
