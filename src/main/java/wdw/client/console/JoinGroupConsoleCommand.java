package wdw.client.console;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import wdw.protocal.request.JoinGroupRequestPacket;
import wdw.util.SessionUtil;

import java.util.Date;
import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner sc, Channel ch) {
        System.out.println("please input group id :");

        String groupId = sc.next().trim();

        JoinGroupRequestPacket packet = new JoinGroupRequestPacket();
        packet.setGroupId(groupId);

        ch.writeAndFlush(packet);
    }
}
