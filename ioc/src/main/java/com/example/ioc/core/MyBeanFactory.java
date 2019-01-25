package com.example.ioc.core;

public interface MyBeanFactory {

    Object getBeanByName(String name) throws Exception;
}
