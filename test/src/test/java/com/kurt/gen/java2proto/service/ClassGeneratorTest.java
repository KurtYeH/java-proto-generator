package com.kurt.gen.java2proto.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yh
 * @create 2022/3/23 11:49 上午
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClassGeneratorTest {

    @Autowired
    private ClassGenerator classGenerator;

    @Test
    public void testGen() throws Exception {
        classGenerator.gen("com.kurt.gen.domain.person.Person", "paymentbusiness.payorder");
    }

}
