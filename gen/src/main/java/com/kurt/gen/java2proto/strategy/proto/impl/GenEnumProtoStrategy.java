package com.kurt.gen.java2proto.strategy.proto.impl;

import com.kurt.gen.java2proto.annotation.GenProtoClassType;
import com.kurt.gen.java2proto.config.Constant;
import com.kurt.gen.java2proto.strategy.proto.GenProtoStrategy;
import com.kurt.gen.java2proto.utils.ClassUtils;
import com.kurt.gen.java2proto.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yh
 * @create 2022/5/26 10:51 上午
 * @desc
 */
@GenProtoClassType(value = java.lang.Enum.class)
@Service
public class GenEnumProtoStrategy implements GenProtoStrategy {

    @Override
    public void generate(Class<?> fieldType) {

        if (Constant.AREADY_GENEDS.contains(fieldType.getSimpleName())) {
            return;
        }

        Constant.AREADY_GENEDS.add(fieldType.getSimpleName());
        genEnumGrpc(fieldType);
    }

    private void genEnumGrpc(Class<?> fieldType) {
        try {

            String protoName = fieldType.getSimpleName() + "Grpc";
            File enumProtoFolder = new File("src/main/proto/" + "enumerate");
            Field[] fieldTypeFields = fieldType.getFields();

            List<Field> fieldWhichTypeIsEnum = Arrays.stream(fieldTypeFields)
                .filter(fieldTypeField -> fieldTypeField.getType().isEnum())
                .collect(Collectors.toList());

            StringBuilder enumProtoParameters = new StringBuilder();
            int enumParameterCount = 0;
            for (Field field : fieldWhichTypeIsEnum) {
                field.setAccessible(true);

                String enumProtoParameters1;

                enumProtoParameters1 = Constant.FOUR_BLANK
                    + field.getName()
                    + Constant.ONE_BLANK
                    + Constant.EQUAL_SYMBOL
                    + Constant.ONE_BLANK
                    + enumParameterCount++
                    + Constant.COMMA
                    + Constant.NEW_LINE;
                enumProtoParameters.append(enumProtoParameters1);
            }

            String protoHead = String.format(Constant.PROTO_HEAD, "enumerate", Constant.PACKAGE_NAME_PREFIX, "enumerate");

            String enumGrpcFileData = protoHead
                + Constant.NEW_LINE
                + Constant.ENUM
                + Constant.ONE_BLANK
                + protoName
                + Constant.ONE_BLANK
                + "{"
                + Constant.NEW_LINE
                + enumProtoParameters
                + "}";

            String protoFileName = protoName + ".proto";
            File file = new File(protoFileName);
            OutputStream output = new FileOutputStream(enumProtoFolder + "/" + file);
            byte[] data = enumGrpcFileData.getBytes();
            output.write(data);
            output.close();
        } catch (IOException var0) {
            System.out.println(var0.getMessage());
        }
    }

    @Override
    public void generateImport(Class<?> fieldType, String serviceModule, StringBuilder importProtoSB) {

        StringBuilder importProtoSBTemp = new StringBuilder("import")
            .append(Constant.ONE_BLANK)
            .append(Constant.DOUBLE_QUOTES)
            .append("enumerate/")
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
