package com.example.ioc.domain;

import com.example.ioc.annotion.MyIoc;
import com.example.ioc.annotion.MyIocUse;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-22 20:46
 **/
@Data
public class BeanDefinition {

    @MyIocUse
    private String className;
    private String alias;
    private String superNames;
}
