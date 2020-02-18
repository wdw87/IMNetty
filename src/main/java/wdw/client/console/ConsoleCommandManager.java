package wdw.client.console;

import io.netty.channel.Channel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("login", new LoginConsoleCommand());
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner sc, Channel ch) {

        String command = sc.next();

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if(consoleCommand != null){
            consoleCommand.exec(sc, ch);
        }else{
            System.err.println(new Date() + ": command err [" + command + "]..");
        }

    }
}
