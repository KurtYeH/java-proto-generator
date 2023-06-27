package com.kurt.gen.java2proto.strategy.proto;

/**
 * @author yh
 * @create 2022/5/25 7:47 下午
 * @desc
 */
public interface GenProtoStrategy {

    void generate(Class<?> fieldType);

    void generateImport(Class<?> fieldType, String serviceModule, StringBuilder imports);

}
