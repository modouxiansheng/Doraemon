package com.example.ioc.domain;

import com.example.ioc.annotion.MyIoc;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf@suixingpay.com
 * @create: 2019-01-23 20:25
 **/
@MyIoc
public class Student {
    public String play(){
        return "student"+ this.toString();
    }
}
