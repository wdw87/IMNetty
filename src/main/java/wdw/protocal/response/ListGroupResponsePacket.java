package wdw.protocal.response;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;
import wdw.session.Session;

import java.util.List;

@Data
public class ListGroupResponsePacket extends Packet {
    private boolean success;
    private String reason;
    private String groupId;
    private List<Session> sessionList;
    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_RESPONSE;
    }
}
