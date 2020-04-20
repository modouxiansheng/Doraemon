package com.example.transaction.transaction.mq.common;

public enum RoutKeyEnum {

    TEST("test"),
    TEST2("test2")
    ;

    String routKey;

    RoutKeyEnum(String routKey){
        this.routKey = routKey;
    }

    public String getRoutKey(){
        return routKey;
    }
}
