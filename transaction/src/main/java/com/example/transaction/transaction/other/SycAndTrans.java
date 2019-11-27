package com.example.transaction.transaction.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.jvm.hotspot.utilities.Interval;

/**
 * @program: springBootPractice
 * @description: 同步锁和事务关键字
 * @author: hu_pf
 * @create: 2019-11-27 11:47
 **/
@Service
@Transactional
public class SycAndTrans {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testAdd(){
        Integer integer = jdbcTemplate.queryForObject("SELECT ID FROM USER WHERE NAME = 'A'", Integer.class);
        integer ++ ;
        jdbcTemplate.execute("UPDATE USER SET ID = "+integer+"");
    }

}
