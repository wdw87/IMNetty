package wdw.protocal.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toId;

    private String message;

    public MessageRequestPacket(String toId, String message){
        this.toId = toId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
