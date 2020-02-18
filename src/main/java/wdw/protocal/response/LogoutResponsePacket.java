package wdw.protocal.response;

import wdw.protocal.Packet;
import wdw.protocal.command.Command;

public class LogoutResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
