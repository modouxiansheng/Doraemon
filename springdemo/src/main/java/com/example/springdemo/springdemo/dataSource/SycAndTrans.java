package com.example.springdemo.springdemo.dataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @program: springBootPractice
 * @description: 同步锁和事务关键字
 * @author: hu_pf
 * @create: 2019-11-27 11:47
 **/
@Service
//@Transactional
@Slf4j
public class SycAndTrans {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

//    @Transactional
    public synchronized void testAdd(){
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        Integer integer = jdbcTemplate.queryForObject("SELECT ID FROM USER WHERE NAME = 'A'", Integer.class);
        integer ++ ;
//        log.info(String.valueOf(integer));
        jdbcTemplate.execute("UPDATE USER SET ID = "+integer+"");
        dataSourceTransactionManager.commit(transaction);
    }

}
