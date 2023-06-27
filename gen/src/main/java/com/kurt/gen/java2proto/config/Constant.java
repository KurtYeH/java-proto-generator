package com.kurt.gen.java2proto.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yh
 * @create 2022/3/18 4:16 下午
 * @desc
 */
public class Constant {

    public static final String PACKAGE_NAME_PREFIX = "com.kurt.gen.";

    public static final String PROTO_HEAD = "syntax = \"proto3\";\n" + "\n" + "package %s;\n"
        + "option java_package = \"%s%s\";\n" + "option java_multiple_files = true;\n";

    public static final String MESSAGE = "message";

    public static final String ENUM = "enum";

    public static final String NEW_LINE = "\n";

    public static final String EMPTY = "";
    public static final String ONE_BLANK = " ";
    public static final String FOUR_BLANK = "    ";

    public static final String EQUAL_SYMBOL = "=";

    public static final String COMMA = ";";

    public static final String DOUBLE_QUOTES = "\"";

    public static final String PROTO_FILE_TYPE = ".proto";

    public static final String JAVA_LANG_OBJECT = "java.lang.Object";

    public static final String JAVA_LANG_ENUM = "java.lang.Enum";

    public static final List<String> NO_NEED_GEN_CLASS = new ArrayList<>();
    static {
        NO_NEED_GEN_CLASS.add(JAVA_LANG_OBJECT);
        NO_NEED_GEN_CLASS.add(JAVA_LANG_ENUM);
    }

    public static final List<Class<?>> ALL_WRAP_CLASS = new ArrayList<>();
    static {
        ALL_WRAP_CLASS.add(Boolean.class);
        ALL_WRAP_CLASS.add(Byte.class);
        ALL_WRAP_CLASS.add(Character.class);
        ALL_WRAP_CLASS.add(Short.class);
        ALL_WRAP_CLASS.add(Integer.class);
        ALL_WRAP_CLASS.add(Long.class);
        ALL_WRAP_CLASS.add(Double.class);
        ALL_WRAP_CLASS.add(Float.class);
        ALL_WRAP_CLASS.add(Void.TYPE);
    }

    public static final List<String> AREADY_GENEDS = new ArrayList<>();

}
