package com.example.springdemo.springdemo.mybatisInterceptor;


import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.InterceptorChain;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public aspect InterceptorConfig implements CommandLineRunner {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MapInterceptor mapInterceptor;

    @Override
    public void run(String... args) throws Exception {
        sqlSessionFactory.getConfiguration().getInterceptors().add(mapInterceptor);
    }
}
