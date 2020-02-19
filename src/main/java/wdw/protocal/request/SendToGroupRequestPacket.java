package wdw.protocal.request;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
public class SendToGroupRequestPacket extends Packet {
    private String gid;
    private String msg;
    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
