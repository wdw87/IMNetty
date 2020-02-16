package wdw.Attributes;

import io.netty.util.AttributeKey;
import wdw.session.Session;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
