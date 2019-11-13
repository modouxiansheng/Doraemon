package com.example.transaction.transaction.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @program: testSpring
 * @description:
 * @author: hu_pf
 * @create: 2019-11-10 00:26
 **/
@Configuration
public class InitPrintChainPattern {

    @Autowired
    private List<PrintChainPattern> printChainPatterns;

    @PostConstruct
    private void initPrintChainPattern(){
        Collections.sort(printChainPatterns, AnnotationAwareOrderComparator.INSTANCE);

        int size = printChainPatterns.size();
        for (int i = 0; i < size; i++) {
            if (i == size-1){
                printChainPatterns.get(i).setNext(null);
            }else {
                printChainPatterns.get(i).setNext(printChainPatterns.get(i+1));
            }
        }
    }

    @Bean
    public User setUser(){
        return User.builder().name("张三").age(14).build();
    }

    public void print(int index){
        printChainPatterns.get(index-1).print();
    }
}
