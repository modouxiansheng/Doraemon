package com.example.transaction.transaction.chain;

import lombok.Builder;
import lombok.Data;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-11-13 19:53
 **/
@Data
@Builder
public class User {
    private String name;

    private Integer age;
}
