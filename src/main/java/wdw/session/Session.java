package wdw.session;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {
    String userId;
    String userName;

    public Session(String name, String id){
        this.userId = id;
        this.userName = name;
    }

    public String toString(){
        return userId + ":" + userName;
    }
}
