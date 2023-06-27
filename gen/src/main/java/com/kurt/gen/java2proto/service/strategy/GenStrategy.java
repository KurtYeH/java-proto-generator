package com.kurt.gen.java2proto.service.strategy;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author yh
 * @create 2022/3/23 11:40 上午
 * @desc
 */
public interface GenStrategy {

    /**
     * 生成字节码
     * @param cn            ASM类节点类
     * @param clazzPath     JavaBean路径
     */
    void generate(ClassNode cn, String clazzPath);

}
