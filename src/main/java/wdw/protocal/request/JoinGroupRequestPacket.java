package wdw.protocal.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
@NoArgsConstructor
public class JoinGroupRequestPacket extends Packet {
    private String groupId;

    public JoinGroupRequestPacket(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
