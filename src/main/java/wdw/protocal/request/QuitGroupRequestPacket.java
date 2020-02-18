package wdw.protocal.request;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
public class QuitGroupRequestPacket extends Packet {
    private String groupId;
    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
