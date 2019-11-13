package com.example.transaction.transaction.chain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 * @program: springBootPractice
 * @description: 人类枚举
 * @author: hu_pf
 * @create: 2019-11-13 20:02
 **/
@Slf4j
public enum  HumanEnum {

    MAN("man"){
        @Override
        public void invoke() {
            log.info("name:{},age:{}",user.getName(),user.getAge());
            log.info("i am man");
        }
    },
    WOMAN("woman"){
        @Override
        public void invoke() {
            log.info("name:{},age:{}",user.getName(),user.getAge());
            log.info("i am woman");
        }
    };

    String value;

    HumanEnum(String value){
        this.value = value;
    }

    public abstract void invoke();

    User user;

    public void setUse(User user){
        this.user = user;
    }

    @Component
    public static class HumanEnumInjector{

        @Autowired
        private  User user;

        @PostConstruct
        public void setValue(){
            for (HumanEnum humanEnum : EnumSet.allOf(HumanEnum.class)){
                humanEnum.setUse(user);
            }
        }
    }


}
