package wdw.protocal.response;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
public class SendToGroupResponsePacket extends Packet {
    private String fromId;
    private String fromName;
    private String msg;
    private boolean success;
    private String reason;
    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
