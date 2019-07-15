package com.example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-07-13 00:09
 **/
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FileDefine {

    public static String WATCH_PACKAGE = System.getProperty("user.home")+"/git/SpringBoot-Practice/classLoader/out/production/classes/com/example/watchfile";

    private String fileName;

    private Long lastDefine;

}
