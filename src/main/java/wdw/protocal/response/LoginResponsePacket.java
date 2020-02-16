package wdw.protocal.response;

import lombok.Data;
import wdw.protocal.Packet;
import wdw.protocal.command.Command;

@Data
public class LoginResponsePacket extends Packet {

    private String userName;

    private String userId;

    private boolean success;

    private String reason;

    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
