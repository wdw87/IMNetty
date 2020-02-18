package wdw.protocal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import wdw.protocal.command.Command;
import wdw.protocal.request.CreateGroupRequestPacket;
import wdw.protocal.request.LoginRequestPacket;
import wdw.protocal.request.LogoutRequestPacket;
import wdw.protocal.request.MessageRequestPacket;
import wdw.protocal.response.CreateGroupResponsePacket;
import wdw.protocal.response.LoginResponsePacket;
import wdw.protocal.response.LogoutResponsePacket;
import wdw.protocal.response.MessageResponsePacket;
import wdw.serialize.Serializer;
import wdw.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;


public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> requestTypeMap;
    private final Map<Byte, Serializer> serializerTypeMap;

    public PacketCodeC(){
        requestTypeMap = new HashMap<Byte, Class<? extends Packet>>();
        serializerTypeMap = new HashMap<Byte, Serializer>();

        requestTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        requestTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        requestTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        requestTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        requestTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        requestTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        requestTypeMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        requestTypeMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);

        Serializer serializer = new JSONSerializer();
        serializerTypeMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet){
        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf){
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Class<? extends Packet> getRequestType(byte command){
        return requestTypeMap.get(command);
    }

    private Serializer getSerializer(byte serializerAlgorithm){
        return serializerTypeMap.get(serializerAlgorithm);
    }


}
