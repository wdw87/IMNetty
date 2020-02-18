package wdw.protocal.request;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userList;
    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
