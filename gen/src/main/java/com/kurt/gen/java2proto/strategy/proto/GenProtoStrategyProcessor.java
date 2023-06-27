package com.kurt.gen.java2proto.strategy.proto;

import com.google.common.collect.Maps;
import com.kurt.gen.java2proto.annotation.GenProtoClassType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yh
 * @create 2022/5/26 9:19 上午
 * @desc
 */
@Component
public class GenProtoStrategyProcessor implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> genProtoStrategyMap = applicationContext.getBeansWithAnnotation(GenProtoClassType.class);
        genProtoStrategyMap.forEach((k, v) -> {
            Class<GenProtoStrategy> clazz = (Class<GenProtoStrategy>) v.getClass();
            Class<?> classType = clazz.getAnnotation(GenProtoClassType.class).value();
            System.out.println("genProtoClassType: " + classType + ", strategy: " + clazz);
            GenProtoStrategyContext.genServiceBeanMap.put(classType, clazz);
        });

        for (Map.Entry<Class<?>, Class<? extends GenProtoStrategy>> entry : GenProtoStrategyContext.genServiceBeanMap.entrySet()) {
            GenProtoStrategyContext.STRATEGY_BEAN_MAP.put(entry.getValue(), applicationContext.getBean(entry.getValue()));
        }
    }

}
