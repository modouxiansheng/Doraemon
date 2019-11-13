package com.example.transaction.transaction.chain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: testSpring
 * @description:
 * @author: hu_pf
 * @create: 2019-11-10 00:25
 **/
@Component
@Order(3)
public class ThreePrintChainPattern extends PrintChainPattern{
    @Override
    public String getMessage() {
        HumanEnum.WOMAN.invoke();
        return "Three";
    }
}
