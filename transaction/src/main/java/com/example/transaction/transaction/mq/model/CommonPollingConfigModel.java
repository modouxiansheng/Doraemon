package com.example.transaction.transaction.mq.model;

import com.example.transaction.transaction.mq.common.RoutKeyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: pay
 * @description: 延时任务实体类
 * @author: hu_pf
 * @create: 2020-04-09 16:01
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPollingConfigModel {

    // 是否是阶梯式延时任务
    private Boolean ifStep;

    // 延时时间
    private Integer delayTime;

    // 已执行的次数
    private Integer sn;

    // 延时数组
    private Integer[] delayTimeArray;

    private String sendMsgJson;

    private RoutKeyEnum routKey;

    // 最大重试次数
    private Integer maxPolling;
}
