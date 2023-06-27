package com.kurt.gen.java2proto.service.strategy;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author yh
 * @create 2021/12/2 9:23 上午
 * @desc
 */
@Component
public class GenStrategyContext {

    @Autowired
    private ApplicationContext applicationContext;

    public static Map<Class<?>, Class<? extends GenStrategy>> genServiceBeanMap = Maps.newHashMap();

    public GenStrategy getGenService(Class<?> classType) {
        Class<? extends GenStrategy> clazz = genServiceBeanMap.get(classType);
        if (Objects.isNull(clazz)) {
            System.out.println("不支持的参数类型");
        }
        // 从容器中获取对应的Bean
        return applicationContext.getBean(clazz);
    }

}
