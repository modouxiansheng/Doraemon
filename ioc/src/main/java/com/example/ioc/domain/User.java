package com.example.ioc.domain;

import com.example.ioc.annotion.MyIoc;
import lombok.Data;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-23 20:25
 **/
@MyIoc
@Data
public class User {
    private Student student;
}
