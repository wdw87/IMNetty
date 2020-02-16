package wdw.protocal.response;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
public class MessageResponsePacket extends Packet {

    private String fromId;

    private String fromName;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
