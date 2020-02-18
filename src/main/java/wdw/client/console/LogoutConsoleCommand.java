package wdw.client.console;

import io.netty.channel.Channel;
import wdw.protocal.request.LogoutRequestPacket;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel ch) {
        LogoutRequestPacket packet = new LogoutRequestPacket();

        ch.writeAndFlush(packet);
    }
}
