package com.example.transaction.transaction.mq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: pay
 * @description:
 * @author: hu_pf
 * @create: 2020-04-09 16:43
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPollingConfigReturnModel {

    private Boolean ifSend;


}
