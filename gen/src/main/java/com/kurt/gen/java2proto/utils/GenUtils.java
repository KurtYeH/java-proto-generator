package com.kurt.gen.java2proto.utils;

import com.kurt.gen.java2proto.config.Constant;

import static com.kurt.gen.java2proto.config.Constant.NO_NEED_GEN_CLASS;

/**
 * @author yh
 * @create 2023/1/9 10:10
 * @desc
 */
public class GenUtils {

    public static boolean superClassNeedGen(Class<?> clazz) {

        Class<?> superclass = clazz.getSuperclass();
        if (!NO_NEED_GEN_CLASS.contains(superclass.getName())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 传入路径，返回是否是绝对路径，是绝对路径返回true，反之相对路径
     */
    public static boolean isAbsolutePath(String path) {
        return path.startsWith("/") || path.indexOf(":") > 0;
    }

}
