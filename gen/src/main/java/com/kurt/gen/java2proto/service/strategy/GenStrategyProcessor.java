package com.kurt.gen.java2proto.service.strategy;

import com.kurt.gen.java2proto.annotation.ClassType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yh
 * @create 2021/12/2 9:38 上午
 * @desc
 */
@Component
public class GenStrategyProcessor implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> payeeInfoSaveServiceMap = applicationContext.getBeansWithAnnotation(ClassType.class);
        payeeInfoSaveServiceMap.forEach((k, v) -> {
            Class<GenStrategy> clazz = (Class<GenStrategy>) v.getClass();
            Class<?> classType = clazz.getAnnotation(ClassType.class).value();
            /*logger.info("formType: {}, strategy: {}", formType, clazz);*/
            System.out.println("classType: {}" + classType + "strategy: {}" + clazz);
            GenStrategyContext.genServiceBeanMap.put(classType, clazz);
        });
    }

}
