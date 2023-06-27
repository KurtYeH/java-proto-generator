package com.kurt.gen.java2proto.service.strategy.impl;

import com.kurt.gen.java2proto.annotation.ClassType;
import com.kurt.gen.java2proto.service.strategy.GenStrategy;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author yh
 * @create 2022/3/23 11:39 上午
 * @desc
 */
@ClassType(value = List.class)
@Service
public class ListGenStrategy implements GenStrategy {

    @Override
    public void generate(ClassNode cn, String clazzPath) {

        MethodNode methodNode = new MethodNode(ACC_PUBLIC,
            "toGrpcs",
            "(Ljava/util/List;)Ljava/util/List;",
            String.format("(Ljava/util/List<L%s;>;)Ljava/util/List<Lcom/kurt/gen/paymentbusiness/payorder/PersonGrpc;>;", clazzPath),
            null);
        cn.methods.add(methodNode);

        InsnList il = methodNode.instructions;
        il.add(new VarInsnNode(ALOAD, 1));
        il.add(new MethodInsnNode(INVOKESTATIC,
            "org/apache/commons/collections4/CollectionUtils",
            "emptyIfNull",
            "(Ljava/util/Collection;)Ljava/util/Collection;",
            false));
        il.add(new MethodInsnNode(INVOKEINTERFACE, "java/util/Collection", "stream", "()Ljava/util/stream/Stream;", true));
        il.add(new VarInsnNode(ALOAD, 0));
        il.add(new InvokeDynamicInsnNode("apply",
            "(Lcom/kurt/gen/java2proto/result/com.kurt.gen.java2proto.result.PersonConverter;)Ljava/util/function/Function;",
            new Handle(Opcodes.H_INVOKESTATIC,
                "java/lang/invoke/LambdaMetafactory",
                "metafactory",
                "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                false),
            Type.getType("(Ljava/lang/Object;)Ljava/lang/Object;"),
            new Handle(Opcodes.H_INVOKEVIRTUAL,
                "com/kurt/gen/java2proto/result/com.kurt.gen.java2proto.result.PersonConverter",
                "toGrpc",
                String.format("(L%s;)Lcom/kurt/gen/paymentbusiness/payorder/PersonGrpc;", clazzPath),
                false),
            Type.getType(String.format("(L%s;)Lcom/kurt/gen/paymentbusiness/payorder/PersonGrpc;",
                clazzPath
                )
            ))
        );
        il.add(new MethodInsnNode(INVOKEINTERFACE,
            "java/util/stream/Stream",
            "map",
            "(Ljava/util/function/Function;)Ljava/util/stream/Stream;",
            true));
        il.add(new MethodInsnNode(INVOKESTATIC,
            "java/util/stream/Collectors",
            "toList",
            "()Ljava/util/stream/Collector;",
            false));
        il.add(new MethodInsnNode(INVOKEINTERFACE,
            "java/util/stream/Stream",
            "collect",
            "(Ljava/util/stream/Collector;)Ljava/util/List;",
            true));
        // il.add(new TypeInsnNode(CHECKCAST, "java/util/List"));
        il.add(new InsnNode(ARETURN));

        methodNode.maxStack = 2;
        methodNode.maxLocals = 2;
    }
}
