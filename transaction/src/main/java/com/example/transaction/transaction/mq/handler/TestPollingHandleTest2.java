package com.example.transaction.transaction.mq.handler;

import com.example.transaction.transaction.mq.annotion.PollingConfig;
import com.example.transaction.transaction.mq.common.RoutKeyEnum;
import com.example.transaction.transaction.mq.model.CommonPollingConfigModel;
import com.example.transaction.transaction.mq.model.CommonPollingConfigReturnModel;
import org.springframework.stereotype.Service;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-04-10 22:44
 **/
@Service
@PollingConfig(routKey = RoutKeyEnum.TEST2)
public class TestPollingHandleTest2 implements PollingHandle{
    @Override
    public CommonPollingConfigReturnModel invoke(CommonPollingConfigModel model) {

        return CommonPollingConfigReturnModel.builder().ifSend(true).build();
    }
}
