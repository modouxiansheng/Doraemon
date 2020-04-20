package com.example.transaction.transaction.mq.annotion;


import com.example.transaction.transaction.mq.common.RoutKeyEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PollingConfig {

    RoutKeyEnum routKey();
}
