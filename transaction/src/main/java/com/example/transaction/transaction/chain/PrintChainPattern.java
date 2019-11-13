package com.example.transaction.transaction.chain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: testSpring
 * @description:
 * @author: hu_pf
 * @create: 2019-11-10 00:23
 **/
@Slf4j
@Data
public abstract class PrintChainPattern {

    private PrintChainPattern next;

    private User user;

    @Autowired
    public void setUser(User user){
        this.user = user;
    }

    public final void print() {
        String message = getMessage();

        log.info("{} : {}",message,message);
        if (getNext()!=null){
            getNext().print();
        }
    }


    public abstract String getMessage();
}
