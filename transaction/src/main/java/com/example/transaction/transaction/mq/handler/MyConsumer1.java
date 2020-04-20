package com.example.transaction.transaction.mq.handler;

import com.alibaba.fastjson.JSON;
import com.example.transaction.transaction.mq.model.CommonPollingConfigModel;
import com.example.transaction.transaction.mq.model.CommonPollingConfigReturnModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-04-09 18:44
 **/
@Service
@Slf4j
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
public class MyConsumer1 implements RocketMQListener<String> {

    @Autowired
    private PollingHandleRouter pollingHandleRouter;



    @Override
    public void onMessage(String message) {

        CommonPollingConfigModel commonPollingConfigModel = JSON.parseObject(message, CommonPollingConfigModel.class);
        PollingHandle pollingHandle = pollingHandleRouter.getPollingHandle(commonPollingConfigModel.getRoutKey());

        CommonPollingConfigReturnModel invoke = pollingHandle.invoke(commonPollingConfigModel);
        Integer sn = commonPollingConfigModel.getSn();
        sn++;
        if (invoke.getIfSend()){

            if (!ObjectUtils.isEmpty(commonPollingConfigModel.getIfStep())&&commonPollingConfigModel.getIfStep()){

                if (sn<=commonPollingConfigModel.getDelayTimeArray().length){
                    commonPollingConfigModel.setSn(sn);
                    pollingHandleRouter.send(commonPollingConfigModel);
                }

            }else {
                if (commonPollingConfigModel.getSn()<commonPollingConfigModel.getMaxPolling()){
                    commonPollingConfigModel.setSn(sn);
                    pollingHandleRouter.send(commonPollingConfigModel);
                }
            }
        }

        log.info("received message: {}", message);
    }
}
