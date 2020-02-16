package wdw.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import wdw.Attributes.Attributes;
import wdw.session.Session;

import java.util.HashMap;
import java.util.Map;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new HashMap<>();

    public static void bingChannel(Session session, Channel ch){
        userIdChannelMap.put(session.getUserId(), ch);
        ch.attr(Attributes.SESSION).set(session);
    }

    public static void unBindChannel(Channel ch){
        userIdChannelMap.remove(getSession(ch).getUserId());
        ch.attr(Attributes.SESSION).set(null);
    }

    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();

    }

    public static Channel getChannel(String id){
        return userIdChannelMap.get(id);
    }


    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hadLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
