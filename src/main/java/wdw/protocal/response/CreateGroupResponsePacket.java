package wdw.protocal.response;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet {
    boolean success;
    String groupId;
    List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
