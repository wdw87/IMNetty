package wdw.client.console;

import io.netty.channel.Channel;
import wdw.protocal.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLIT = ",";
    @Override
    public void exec(Scanner sc, Channel ch) {
        String[] users = sc.nextLine().trim().split(USER_ID_SPLIT);

        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();

        packet.setUserList(Arrays.asList(users));

        ch.writeAndFlush(packet);

    }
}
