package wdw.protocal.response;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
public class JoinGroupResponsePacket extends Packet {
    private boolean success;
    private String reason;
    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
