package com.kurt.gen.java2proto.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yh
 * @create 2022/5/30 6:54 下午
 * @desc
 */
public class JavaProtoConstant {

    private static final Map<String, String> JAVA_PROTO_MAPS = new HashMap<>(32);
    static {
        JAVA_PROTO_MAPS.put("double", "double");
        JAVA_PROTO_MAPS.put("java.lang.Double", "double");
        JAVA_PROTO_MAPS.put("float", "float");
        JAVA_PROTO_MAPS.put("java.lang.Float", "float");
        JAVA_PROTO_MAPS.put("int", "int32");
        JAVA_PROTO_MAPS.put("java.lang.Integer", "int32");
        JAVA_PROTO_MAPS.put("long", "int64");
        JAVA_PROTO_MAPS.put("java.lang.Long", "int64");
        JAVA_PROTO_MAPS.put("boolean", "bool");
        JAVA_PROTO_MAPS.put("java.lang.Boolean", "bool");
        JAVA_PROTO_MAPS.put("byte", "bytes");
        JAVA_PROTO_MAPS.put("java.lang.Byte", "bytes");
        JAVA_PROTO_MAPS.put("short", "int32");
        JAVA_PROTO_MAPS.put("java.lang.Short", "int32");
        JAVA_PROTO_MAPS.put("char", "int32");
        JAVA_PROTO_MAPS.put("java.lang.Character", "int32");
        JAVA_PROTO_MAPS.put("java.lang.String", "string");
        JAVA_PROTO_MAPS.put("java.util.Date", "int64");
        JAVA_PROTO_MAPS.put("java.math.BigDecimal", "common.BigDecimalGrpc");
        JAVA_PROTO_MAPS.put("java.util.Collection", "repeated");
    }

    public static Map<String, String> getJavaProtoMaps() {
        return JAVA_PROTO_MAPS;
    }

}
