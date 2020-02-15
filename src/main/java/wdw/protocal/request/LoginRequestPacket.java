package wdw.protocal.request;

import lombok.Data;
import wdw.protocal.Packet;
import static wdw.protocal.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    private String uesrId;

    private String username;

    private String password;


    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
