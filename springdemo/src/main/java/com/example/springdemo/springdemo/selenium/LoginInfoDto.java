package com.example.springdemo.springdemo.selenium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-05-15 17:47
 **/
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginInfoDto {

    private String loginName;
    private String password;
}
