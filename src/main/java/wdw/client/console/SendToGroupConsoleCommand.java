package wdw.client.console;

import io.netty.channel.Channel;
import wdw.protocal.request.SendToGroupRequestPacket;
import wdw.session.Session;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel ch) {
        System.out.println("please input group id and message :");
        String gid = sc.next().trim();
        String msg = sc.nextLine().trim();

        SendToGroupRequestPacket packet = new SendToGroupRequestPacket();

        packet.setGid(gid);
        packet.setMsg(msg);

        ch.writeAndFlush(packet);
    }
}
