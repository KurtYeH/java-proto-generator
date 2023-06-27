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
import java.util.Arrays;
import java.util.List;

/**
 * @author yh
 * @create 2022/5/26 9:46 上午
 * @desc
 */
@GenProtoClassType(value = java.math.BigDecimal.class)
@Service
public class GenBigDecimalProtoStrategy implements GenProtoStrategy {

    @Override
    public void generate(Class<?> fieldType) {

        if (Constant.AREADY_GENEDS.contains(fieldType.getSimpleName())) {
            return;
        }

        Constant.AREADY_GENEDS.add(fieldType.getSimpleName());
        genBigDecimalGrpc(fieldType.getSimpleName());
    }

    private void genBigDecimalGrpc(String fieldName) {
        try {
            String protoName = fieldName + "Grpc";
            File commonProtoFolder = new File("src/main/proto/" + "common");
            String protoHead = String.format(Constant.PROTO_HEAD, "common", Constant.PACKAGE_NAME_PREFIX, "common");

            String enumGrpcFileData = protoHead
                + Constant.NEW_LINE
                + Constant.MESSAGE
                + Constant.ONE_BLANK
                + protoName
                + Constant.ONE_BLANK
                + "{"
                + Constant.NEW_LINE
                + Constant.FOUR_BLANK
                + "string value = 1;"
                + Constant.NEW_LINE
                + "}";

            String protoFileName = protoName + ".proto";
            File file = new File(protoFileName);
            OutputStream output = new FileOutputStream(commonProtoFolder + "/" + file);
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
            .append("common/")
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
