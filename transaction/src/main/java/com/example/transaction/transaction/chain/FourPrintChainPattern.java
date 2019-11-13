package com.example.transaction.transaction.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: testSpring
 * @description:
 * @author: hu_pf
 * @create: 2019-11-10 00:43
 **/
@Component
@Order(4)
public class FourPrintChainPattern extends PrintChainPattern{
    @Override
    public String getMessage() {
        HumanEnum.MAN.invoke();
        return "Four";
    }
}
