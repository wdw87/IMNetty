package wdw.protocal.request;

import wdw.protocal.Packet;
import wdw.protocal.command.Command;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
