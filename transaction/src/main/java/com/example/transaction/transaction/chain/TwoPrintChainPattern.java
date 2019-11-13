package com.example.transaction.transaction.chain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: testSpring
 * @description:
 * @author: hu_pf
 * @create: 2019-11-10 00:25
 **/
@Slf4j
@Component
@Order(2)
public class TwoPrintChainPattern extends PrintChainPattern{
    @Override
    public String getMessage() {
        log.info("name:{},age:{}",getUser().getName(),getUser().getAge());
        return "two";
    }
}
