package com.kurt.gen.java2proto.service;

import com.kurt.gen.java2proto.config.Constant;
import com.kurt.gen.java2proto.service.strategy.GenStrategyContext;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author yh
 * @create 2022/3/16 8:06 下午
 * @desc
 */
@Service
public class ClassGenerator {

    private static final String RESULT_FOLD_NAME;

    static {
        RESULT_FOLD_NAME = System.getProperty("user.dir") + "/result/";
    }

    @Autowired private GenStrategyContext genContext;

    public void gen(String clazzPathStr, String serviceModule) throws Exception {
        // (1) 生成byte[]内容
        byte[] code = dump(clazzPathStr, serviceModule);

        File file = new File(RESULT_FOLD_NAME);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("原来不存在" + file.getAbsolutePath() + "文件夹，现在创建成功！");
            }
        }

        String converterName = clazzPathStr.substring(clazzPathStr.lastIndexOf(".") + 1) + "Converter.class";
        OutputStream fos = new FileOutputStream(file + "/" + converterName);
        fos.write(code);
        fos.close();
    }

    public byte[] dump(String clazzPathStr, String serviceModule) throws ClassNotFoundException {

        Class<?> clazz = Class.forName(clazzPathStr);

        ClassNode cn = new ClassNode();
        cn.version = V11;
        cn.access = ACC_PUBLIC | ACC_SUPER;
        System.out.println(clazz.getName());
        cn.name = "com/kurt/gen/java2proto/result/PersonConverter";
        cn.signature = null;
        cn.superName = "java/lang/Object";

        String grpcClassName = Constant.PACKAGE_NAME_PREFIX +
            StringUtils.replace(serviceModule, ".", "/") +
            "/" +
            clazzPathStr.substring(clazzPathStr.lastIndexOf(".") + 1) +
            "Grpc";

        {
            MethodNode methodNode = new MethodNode(ACC_PRIVATE, "<init>", "()V", null, null);
            cn.methods.add(methodNode);

            InsnList il = methodNode.instructions;
            il.add(new VarInsnNode(ALOAD, 0));
            il.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false));
            il.add(new InsnNode(RETURN));

            methodNode.maxStack = 1;
            methodNode.maxLocals = 1;
        }

        genContext.getGenService(List.class).generate(cn, clazzPathStr);

        {
            MethodNode methodNode = new MethodNode(ACC_PUBLIC, "toGrpc", String.format("(L%s;)L%s;", clazzPathStr, grpcClassName), null, null);
            cn.methods.add(methodNode);

            InsnList il = methodNode.instructions;
            il.add(new VarInsnNode(ALOAD, 1));
            il.add(new MethodInsnNode(INVOKESTATIC, "java/util/Objects", "isNull", "(Ljava/lang/Object;)Z", false));
            LabelNode labelNode0 = new LabelNode();
            il.add(new JumpInsnNode(IFEQ, labelNode0));
            il.add(new MethodInsnNode(INVOKESTATIC, String.format("%s", grpcClassName), "getDefaultInstance",
                String.format("()L%s;", grpcClassName), false));
            il.add(new InsnNode(ARETURN));

            il.add(labelNode0);

            il.add(new MethodInsnNode(INVOKESTATIC, String.format("%s", grpcClassName), "newBuilder",
                String.format("()L%s$Builder;", grpcClassName), false));
            il.add(new VarInsnNode(ASTORE, 2));
            il.add(new MethodInsnNode(INVOKESTATIC, "com/kurt/gen/paymentbusiness/payorder/HumanGrpc", "newBuilder",
                "()Lcom/kurt/gen/paymentbusiness/payorder/HumanGrpc$Builder;", false));
            il.add(new VarInsnNode(ASTORE, 3));
            il.add(new VarInsnNode(ALOAD, 1));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, clazzPathStr, "getHomeStar", "()Ljava/lang/String;", false));
            il.add(new MethodInsnNode(INVOKESTATIC, "org/apache/commons/lang3/StringUtils", "isBlank",
                "(Ljava/lang/CharSequence;)Z", false));
            LabelNode labelNode1 = new LabelNode();
            il.add(new JumpInsnNode(IFEQ, labelNode1));
            il.add(new VarInsnNode(ALOAD, 3));
            il.add(new VarInsnNode(ALOAD, 1));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, clazzPathStr, "getHomeStar", "()Ljava/lang/String;", false));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, "com/kurt/gen/paymentbusiness/payorder/HumanGrpc$Builder",
                "setHomeStarGrpc", "(Ljava/lang/String;)Lcom/kurt/gen/paymentbusiness/payorder/HumanGrpc$Builder;",
                false));
            il.add(new InsnNode(POP));
            il.add(labelNode1);

            il.add(new VarInsnNode(ALOAD, 3));
            il.add(new VarInsnNode(ALOAD, 1));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, clazzPathStr, "getExistYear", "()I", false));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, "com/kurt/gen/paymentbusiness/payorder/HumanGrpc$Builder",
                "setExistYearGrpc", "(I)Lcom/kurt/gen/paymentbusiness/payorder/HumanGrpc$Builder;", false));
            il.add(new InsnNode(POP));
            il.add(new VarInsnNode(ALOAD, 1));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, clazzPathStr, "getDate", "()Ljava/util/Date;", false));
            il.add(new MethodInsnNode(INVOKESTATIC, "java/util/Objects", "nonNull", "(Ljava/lang/Object;)Z", false));
            LabelNode labelNode2 = new LabelNode();
            il.add(new JumpInsnNode(IFEQ, labelNode2));
            il.add(new InsnNode(LCONST_0));
            il.add(new VarInsnNode(ALOAD, 1));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, clazzPathStr, "getDate", "()Ljava/util/Date;", false));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/util/Date", "getTime", "()J", false));
            il.add(new InsnNode(LCMP));
            il.add(new JumpInsnNode(IFEQ, labelNode2));
            il.add(new VarInsnNode(ALOAD, 3));
            il.add(new VarInsnNode(ALOAD, 1));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, clazzPathStr, "getDate", "()Ljava/util/Date;", false));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/util/Date", "getTime", "()J", false));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, "com/kurt/gen/paymentbusiness/payorder/HumanGrpc$Builder",
                "setDateGrpc", "(J)Lcom/kurt/gen/paymentbusiness/payorder/HumanGrpc$Builder;", false));
            il.add(new InsnNode(POP));
            il.add(labelNode2);

            il.add(new VarInsnNode(ALOAD, 2));
            il.add(new VarInsnNode(ALOAD, 3));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, "com/kurt/gen/paymentbusiness/payorder/HumanGrpc$Builder", "build",
                "()Lcom/kurt/gen/paymentbusiness/payorder/HumanGrpc;", false));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, String.format("%s$Builder", grpcClassName), "setHumanGrpc",
                String.format("(Lcom/kurt/gen/paymentbusiness/payorder/HumanGrpc;)L%s$Builder;", grpcClassName),
                false));
            il.add(new InsnNode(POP));
            il.add(new VarInsnNode(ALOAD, 2));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, String.format("%s$Builder", grpcClassName), "build",
                String.format("()L%s;", grpcClassName), false));
            il.add(new InsnNode(ARETURN));

            methodNode.maxStack = 3;
            methodNode.maxLocals = 3;
        }

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cn.accept(cw);
        return cw.toByteArray();

    }

}