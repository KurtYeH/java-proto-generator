package com.kurt.gen.java2proto.utils;

import com.kurt.gen.java2proto.config.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author yh
 * @create 2022/5/11 1:59 下午
 * @desc
 */
public class ClassUtils {

    public static Boolean isTypeBasicOrWrapClass(Class<?> clazz) {
        try {
            return clazz.isPrimitive() || isWrapClass(clazz);
        } catch (Exception var0) {
            return Boolean.FALSE;
        }
    }

    public static Boolean isWrapClass(Class<?> clazz) {
        return Constant.ALL_WRAP_CLASS.contains(clazz);
    }

    public static String getName(Class<?> clazz) {
        return clazz.getName();
    }

    public static String getSimpleName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    /**
     * 是否是Bootstrap ClassLoader类加载器加载的类
     * 相当于判断是否是Java自带的类，而不是用户自定义的类
     * @return  true  自带类
     *          false 用户自定义类
     */
    public static Boolean isBootstrapLoadedJavaClass(Class<?> clazz) {
        return Objects.nonNull(clazz) && Objects.isNull(clazz.getClassLoader());
    }

    public static Boolean isNotBootstrapLoadedJavaClassAndNotEnum(Class<?> clazz) {
        return (!isBootstrapLoadedJavaClass(clazz) && !clazz.isEnum());
    }

    /**
     * 获取泛型的真实类型
     */
    public static Class<?> getParameterizedActualType(Field field) {

        checkNotNull(field);

        if (isCollectionOrItsSubClass(field.getType())) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
            return (Class<?>) actualTypeArgument;
        }

        return Class.class;
    }

    public static Boolean isCollectionOrItsSubClass(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    public static Boolean isArray(Class<?> clazz) {
        return clazz.isArray();
    }

    public static String getGenericTypeSimpleName(Field field) {
        if (isCollectionOrItsSubClass(field.getType())) {
            return Optional.ofNullable(getParameterizedActualType(field)).map(Class::getSimpleName).orElse(Constant.EMPTY);
        }
        return Constant.EMPTY;
    }

    public static <T> void checkNotNull(T parameter) {

        if (parameter == null) {
            throw new RuntimeException("不支持传入参数为空！");
        }
    }

    public static Class<?> initializeClass(String classPath) throws ClassNotFoundException {
        if (GenUtils.isAbsolutePath(classPath)) {
            // 暂时不支持输入类的绝对路径
            throw new RuntimeException("暂时不支持输入类的绝对路径");
            /*File file = new File(classPath);
            URL url = file.toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url});
            return urlClassLoader.loadClass();*/
        }

        return Class.forName(classPath);
    }

}
