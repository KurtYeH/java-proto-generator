package com.kurt.gen.java2proto.service;

import com.kurt.gen.java2proto.config.Constant;
import com.kurt.gen.java2proto.config.JavaProtoConstant;
import com.kurt.gen.java2proto.domain.SelfDefine;
import com.kurt.gen.java2proto.strategy.proto.GenProtoStrategyContext;
import com.kurt.gen.java2proto.utils.ClassUtils;
import com.kurt.gen.java2proto.utils.GenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author yh
 * @create 2022/3/18 4:00 下午
 * @desc
 */
@Service
public class ProtoGenerator {

    public static String PROTO_FILE_HEAD = "";

    @Autowired
    private GenProtoStrategyContext genProtoStrategyContext;

    /**
     *
     * @param clazzStr          要生成proto参数的JavaBean
     * @param serviceModule     服务名.模块名（paymentbusiness.payorder）
     */
    public void gen(String clazzStr, String serviceModule) {

        try {

            System.out.println("1");

            Class<?> clazz = ClassUtils.initializeClass(clazzStr);

            Constant.AREADY_GENEDS.add(clazz.getSimpleName());

            File protoFolder = createDirectory(serviceModule);

            String simpleName = clazz.getSimpleName();
            String protoClazzName = simpleName + "Grpc";

            Class<?> superclass = clazz.getSuperclass();
            // System.out.println(superclass.getName());
            if (GenUtils.superClassNeedGen(clazz)) {
                gen(superclass.getName(), serviceModule);
            }

            int parameterCount = 1;
            StringBuilder protoParameters = new StringBuilder();
            Field[] fields = clazz.getDeclaredFields();
            String protoPackage = getLastWord(serviceModule, ".");

            if (GenUtils.superClassNeedGen(clazz)) {
                String c = String.valueOf((superclass.getSimpleName() + "Grpc").charAt(0)).toLowerCase()
                    + ((superclass.getSimpleName() + "Grpc").substring(1));
                String protoParameter0 = Constant.FOUR_BLANK
                    + protoPackage
                    + "."
                    + superclass.getSimpleName()
                    + "Grpc"
                    + Constant.ONE_BLANK
                    + c
                    + Constant.ONE_BLANK
                    + Constant.EQUAL_SYMBOL
                    + Constant.ONE_BLANK
                    + parameterCount++
                    + Constant.COMMA
                    + Constant.NEW_LINE;
                protoParameters.append(protoParameter0);
            }

            String protoName;

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                String fieldTypeName = fieldType.getName();

                String protoParameter1;

                Class<?> aClass = whichClassType(fieldType);
                if (Objects.nonNull(aClass) && !ClassUtils.isCollectionOrItsSubClass(aClass)) {
                    genProtoStrategyContext.getGenService(aClass).generate(fieldType);
                }

                if (ClassUtils.isNotBootstrapLoadedJavaClassAndNotEnum(fieldType)) {
                    if (!Constant.AREADY_GENEDS.contains(fieldType.getSimpleName())) {
                        gen(fieldTypeName, serviceModule);
                    }
                }

                if (ClassUtils.isCollectionOrItsSubClass(fieldType)) {
                    Class<?> actualType = ClassUtils.getParameterizedActualType(field);
                    if (!ClassUtils.isBootstrapLoadedJavaClass(actualType)) {
                        if (actualType.isEnum()) {
                            genProtoStrategyContext.getGenService(Enum.class).generate(actualType);
                        } else {
                            if (!Constant.AREADY_GENEDS.contains(actualType.getSimpleName())) {
                                gen(actualType.getName(), serviceModule);
                            }
                        }
                    }
                }

                // System.out.println("参数名：" + fieldName + "参数类型：" + fieldTypeName);
                protoName = fieldName + "Grpc";

                protoParameter1 = Constant.FOUR_BLANK
                    + repeat(fieldType)
                    + getProtoType(serviceModule, field)
                    + Constant.ONE_BLANK
                    + protoName
                    + Constant.ONE_BLANK
                    + Constant.EQUAL_SYMBOL
                    + Constant.ONE_BLANK
                    + parameterCount++
                    + Constant.COMMA
                    + Constant.NEW_LINE;
                protoParameters.append(protoParameter1);
            }

            if (StringUtils.isBlank(PROTO_FILE_HEAD)) {
                PROTO_FILE_HEAD = String.format(Constant.PROTO_HEAD, protoPackage, Constant.PACKAGE_NAME_PREFIX, serviceModule);
            }

            String importProto = getImport(serviceModule, clazz);

            String protoFileData = PROTO_FILE_HEAD
                + (StringUtils.isBlank(importProto) ? Constant.EMPTY : Constant.NEW_LINE)
                + importProto
                + Constant.NEW_LINE
                + Constant.MESSAGE
                + Constant.ONE_BLANK
                + protoClazzName
                + Constant.ONE_BLANK
                + "{"
                + Constant.NEW_LINE
                + StringUtils.chop(String.valueOf(protoParameters))
                + Constant.NEW_LINE
                + "}";

            String protoFileName = protoClazzName + ".proto";
            File file = new File(protoFileName);
            OutputStream output = new FileOutputStream(protoFolder + "/" + file);
            byte[] data = protoFileData.getBytes();
            output.write(data);
            output.close();
        } catch (Exception var0) {
            var0.printStackTrace();
        }
    }

    private File createDirectory(String serviceModule) {
        File protoFolder = new File("src/main/proto/" + StringUtils.replace(serviceModule, ".", "/"));
        if (!protoFolder.exists()) {
            if (protoFolder.mkdirs()) {
                System.out.println("原来不存在" + protoFolder.getAbsolutePath() + "文件夹，现在创建成功！");
            }
        }

        File commonProtoFolder = new File("src/main/proto/" + "common");
        if (!commonProtoFolder.exists() || commonProtoFolder.isFile()) {
            if (commonProtoFolder.mkdirs()) {
                System.out.println("原来不存在" + commonProtoFolder.getAbsolutePath() + "文件夹，现在创建成功！");
            }
        }

        File enumProtoFolder = new File("src/main/proto/" + "enumerate");
        if (!enumProtoFolder.exists() || enumProtoFolder.isFile()) {
            if (enumProtoFolder.mkdirs()) {
                System.out.println("原来不存在" + enumProtoFolder.getAbsolutePath() + "文件夹，现在创建成功！");
            }
        }
        return protoFolder;
    }

    private String repeat(Class<?> fieldType) {
        if (ClassUtils.isCollectionOrItsSubClass(fieldType) || ClassUtils.isArray(fieldType)) {
            return JavaProtoConstant.getJavaProtoMaps().get(java.util.Collection.class.getName()) + Constant.ONE_BLANK;
        }

        return Constant.EMPTY;
    }

    private String getProtoType(String serviceModule, Field field) {

        Class<?> fieldType = field.getType();
        String fieldTypeName = fieldType.getName();

        if (!ClassUtils.isCollectionOrItsSubClass(fieldType)) {
            if (fieldType.isEnum()) {
                return "enumerate." + fieldType.getSimpleName() + "Grpc";
            }
        } else if (Optional.ofNullable(ClassUtils.getParameterizedActualType(field)).map(Class::isEnum).orElse(Boolean.FALSE)) {
            return "enumerate." + ClassUtils.getGenericTypeSimpleName(field) + "Grpc";
        }

        if (!ClassUtils.isCollectionOrItsSubClass(fieldType)) {
            if (ClassUtils.isNotBootstrapLoadedJavaClassAndNotEnum(fieldType)) {
                return getLastWord(serviceModule, ".") + "." + fieldType.getSimpleName() + "Grpc";
            }
        } else if (Optional.ofNullable(ClassUtils.getParameterizedActualType(field)).map(ClassUtils::isNotBootstrapLoadedJavaClassAndNotEnum).orElse(Boolean.FALSE)) {
            return getLastWord(serviceModule, ".") + "." + ClassUtils.getGenericTypeSimpleName(field) + "Grpc";
        }

        if (ClassUtils.isCollectionOrItsSubClass(fieldType)) {
            Class<?> actualType = ClassUtils.getParameterizedActualType(field);
            if (Objects.nonNull(actualType)) {
                return JavaProtoConstant.getJavaProtoMaps().get(actualType.getName());
            }
        }

        if (ClassUtils.isArray(fieldType)) {
            return JavaProtoConstant.getJavaProtoMaps().get(fieldType.getComponentType().getName());
        }

        return JavaProtoConstant.getJavaProtoMaps().get(fieldTypeName);
    }

    private String getImport(String serviceModule, Class<?> clazz) {

        if (Objects.isNull(clazz)) {
            return Constant.EMPTY;
        }

        Field[] fields = clazz.getDeclaredFields();

        StringBuilder imports = new StringBuilder();
        if (!Constant.JAVA_LANG_OBJECT.equals(clazz.getSuperclass().getName())) {
            genProtoStrategyContext.getGenService(SelfDefine.class).generateImport(clazz.getSuperclass(), serviceModule, imports);
        }

        for (Field field : fields) {
            field.setAccessible(true);

            Class<?> fieldType = field.getType();

            if (ClassUtils.isTypeBasicOrWrapClass(fieldType)
                || ClassUtils.getName(String.class).equals(fieldType.getName())
                || fieldType.getName().equals(clazz.getName())) {
                continue;
            }

            Class<?> aClass = whichClassType(fieldType);
            if (Objects.isNull(aClass)) {
                continue;
            }

            if (ClassUtils.isCollectionOrItsSubClass(aClass)) {
                Class<?> parameterizedActualType = whichClassType(ClassUtils.getParameterizedActualType(field));
                if (Objects.isNull(parameterizedActualType)) {
                    continue;
                }
                genProtoStrategyContext.getGenService(parameterizedActualType)
                    .generateImport(ClassUtils.getParameterizedActualType(field), serviceModule, imports);
            } else {
                genProtoStrategyContext.getGenService(aClass).generateImport(fieldType, serviceModule, imports);
            }
        }
        return imports.toString();
    }

    private String getLastWord(String str, String splitSymbol) {

        if (StringUtils.isAnyBlank(str, splitSymbol)) {
            return Constant.EMPTY;
        }

        return str.substring(str.lastIndexOf(splitSymbol) + 1);
    }

    private Class<?> whichClassType(Class<?> fieldType) {

        if (Objects.isNull(fieldType)) {
            throw new RuntimeException("参数不能为空");
        }

        if (fieldType.isEnum()) {
            return Enum.class;
        }

        if (ClassUtils.getName(BigDecimal.class).equals(fieldType.getName())) {
            return BigDecimal.class;
        }

        if (ClassUtils.isNotBootstrapLoadedJavaClassAndNotEnum(fieldType)) {
            return com.kurt.gen.java2proto.domain.SelfDefine.class;
        }

        if (ClassUtils.isCollectionOrItsSubClass(fieldType)) {
            return Collection.class;
        }

        return null;
    }

}
