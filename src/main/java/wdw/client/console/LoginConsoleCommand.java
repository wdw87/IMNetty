package wdw.client.console;

import io.netty.channel.Channel;
import wdw.protocal.request.LoginRequestPacket;
import wdw.util.SessionUtil;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel ch) {
        if(!SessionUtil.hadLogin(ch)) {
            String name = sc.next();

            LoginRequestPacket packet = new LoginRequestPacket();
            packet.setUsername(name);
            packet.setPassword("3346535");

            ch.writeAndFlush(packet);
        }else{
            System.out.println("already logged !!");
        }
    }
}
