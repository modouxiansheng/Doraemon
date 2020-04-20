package com.example.transaction.transaction.mq.handler;

import com.alibaba.fastjson.JSON;
import com.example.transaction.transaction.mq.annotion.PollingConfig;
import com.example.transaction.transaction.mq.common.RoutKeyEnum;
import com.example.transaction.transaction.mq.model.CommonPollingConfigModel;
import com.example.transaction.transaction.mq.model.CommonPollingConfigReturnModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-04-09 17:55
 **/
@Service
@PollingConfig(routKey = RoutKeyEnum.TEST)
@Slf4j
public class TestPollingHandle implements PollingHandle{

    @Override
    public CommonPollingConfigReturnModel invoke(CommonPollingConfigModel model) {
        String sendMsgJson = model.getSendMsgJson();
        Stuent stuent = JSON.parseObject(sendMsgJson, Stuent.class);
        log.info("student name:{}",stuent.getName());
        CommonPollingConfigReturnModel commonPollingConfigReturnModel = CommonPollingConfigReturnModel.builder()
                .ifSend(true).build();

        return commonPollingConfigReturnModel;
    }
}
