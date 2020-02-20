package wdw.protocal.request;

import wdw.protocal.Packet;
import wdw.protocal.command.Command;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
