package wdw.protocal.request;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
public class ListGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_REQUEST;
    }
}
