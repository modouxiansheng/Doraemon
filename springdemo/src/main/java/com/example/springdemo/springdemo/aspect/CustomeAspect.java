package com.example.springdemo.springdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-08-12 20:36
 **/
@Component
@Aspect
public class CustomeAspect {
    @Pointcut("execution(public * com.example.springdemo.springdemo.secondAgent..*.*(..))")
    public void getValue(){}

    @Before("getValue()")
    public void doBefore(){
        System.out.println("----------------doBefore");
    }
}
