package wdw.protocal.response;

import wdw.protocal.Packet;
import wdw.protocal.command.Command;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
