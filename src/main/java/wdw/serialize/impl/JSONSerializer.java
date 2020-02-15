package wdw.serialize.impl;

import com.alibaba.fastjson.JSON;
import wdw.serialize.Serializer;
import wdw.serialize.SerializerAlogrithm;

public class JSONSerializer implements Serializer {
    public byte getSerializerAlgorithm() {
        return SerializerAlogrithm.JSON;
    }

    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
