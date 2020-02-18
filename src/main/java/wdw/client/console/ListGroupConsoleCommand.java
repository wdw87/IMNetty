package wdw.client.console;

import io.netty.channel.Channel;
import wdw.protocal.request.ListGroupRequestPacket;

import java.util.Scanner;

public class ListGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel ch) {
        System.out.println("please input group ID to list:");
        String groupId = sc.next().trim();

        ListGroupRequestPacket packet = new ListGroupRequestPacket();
        packet.setGroupId(groupId);

        ch.writeAndFlush(packet);
    }
}
