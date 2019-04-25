package com.example.ioc.domain;

import com.example.ioc.annotion.MyIoc;
import com.example.ioc.annotion.MyIocUse;
import lombok.Data;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-24 20:43
 **/
@MyIoc
@Data
public class MyIocUseDemo {

    @MyIocUse
    private User user;
}
