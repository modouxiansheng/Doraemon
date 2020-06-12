package com.example.springdemo.springdemo.selenium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-05-15 17:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThumbsUpRecordDto {

    private String url;

    private String phones;

    private String userName;
}
