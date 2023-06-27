package com.kurt.gen.java2proto.annotation;

import java.lang.annotation.*;

/**
 * @author yh
 * @create 2022/5/26 9:52 上午
 * @desc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GenProtoClassType {

    Class<?> value() default String.class;

}
