package com.kurt.gen.java2proto.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yh
 * @create 2022/3/21 12:54 下午
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProtoGeneratorTest {

    @Autowired
    private ProtoGenerator protoGenerator;

    @Test
    public void testGen() {
        protoGenerator.gen("com.kurt.gen.domain.reimburse.ApplicationFormDto", "paymentbusiness.payorder");
    }

}
