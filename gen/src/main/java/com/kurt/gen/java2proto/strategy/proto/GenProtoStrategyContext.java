package com.kurt.gen.java2proto.strategy.proto;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yh
 * @create 2022/5/26 9:19 上午
 * @desc
 */
@Component
public class GenProtoStrategyContext {

    public static Map<Class<?>, Class<? extends GenProtoStrategy>> genServiceBeanMap = Maps.newHashMap();
    public static Map<Class<? extends GenProtoStrategy>, Object> STRATEGY_BEAN_MAP = Maps.newHashMap();

    public GenProtoStrategy getGenService(Class<?> classType) {
        Class<? extends GenProtoStrategy> clazz = genServiceBeanMap.get(classType);
        return (GenProtoStrategy) STRATEGY_BEAN_MAP.get(clazz);
    }

}
