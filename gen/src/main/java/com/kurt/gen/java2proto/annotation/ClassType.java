package com.kurt.gen.java2proto.annotation;

import java.lang.annotation.*;

/**
 * @author yh
 * @create 2022/3/23 11:44 上午
 * @desc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ClassType {

    Class<?> value() default String.class;

}
