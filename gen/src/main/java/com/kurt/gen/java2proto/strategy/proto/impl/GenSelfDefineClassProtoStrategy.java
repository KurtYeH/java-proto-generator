package com.kurt.gen.java2proto.strategy.proto.impl;

import com.kurt.gen.java2proto.annotation.GenProtoClassType;
import com.kurt.gen.java2proto.config.Constant;
import com.kurt.gen.java2proto.strategy.proto.GenProtoStrategy;
import com.kurt.gen.java2proto.utils.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author yh
 * @create 2022/5/26 11:34 上午
 * @desc
 */
@GenProtoClassType(value = com.kurt.gen.java2proto.domain.SelfDefine.class)
@Service
public class GenSelfDefineClassProtoStrategy implements GenProtoStrategy {

    @Override
    public void generate(Class<?> fieldType) {

    }

    @Override
    public void generateImport(Class<?> fieldType, String serviceModule, StringBuilder importProtoSB) {

        StringBuilder importProtoSBTemp = new StringBuilder("import")
            .append(Constant.ONE_BLANK)
            .append(Constant.DOUBLE_QUOTES)
            .append(StringUtils.replace(serviceModule, ".", "/"))
            .append("/")
            .append(ClassUtils.getSimpleName(fieldType))
            .append("Grpc")
            .append(Constant.PROTO_FILE_TYPE)
            .append(Constant.DOUBLE_QUOTES)
            .append(Constant.COMMA);

        List<String> importProtos = Arrays.asList(importProtoSB.toString().split(Constant.NEW_LINE));
        if (importProtos.stream().noneMatch(importProto -> importProto.equals(importProtoSBTemp.toString()))) {
            importProtoSB.append(importProtoSBTemp.append(Constant.NEW_LINE));
        }
    }

}
