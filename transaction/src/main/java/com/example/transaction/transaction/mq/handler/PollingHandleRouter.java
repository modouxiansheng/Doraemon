package com.example.transaction.transaction.mq.handler;

import com.alibaba.fastjson.JSON;
import com.example.transaction.transaction.mq.annotion.PollingConfig;
import com.example.transaction.transaction.mq.common.RoutKeyEnum;
import com.example.transaction.transaction.mq.model.CommonPollingConfigModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: pay
 * @description:
 * @author: hu_pf
 * @create: 2020-04-09 16:45
 **/
@Slf4j
@Configuration
public class PollingHandleRouter implements CommandLineRunner {

    public Map<String,PollingHandle> pollingHandleMap = new HashMap<>();

    @Autowired
    private List<PollingHandle> pollingHandleList;

    @Autowired
    private DefaultMQProducer rocketMqProducer;

    @Override
    public void run(String... args) throws Exception {

        for (PollingHandle pollingHandle : pollingHandleList){
            Class<? extends PollingHandle> aClass = pollingHandle.getClass();
            PollingConfig annotation = aClass.getAnnotation(PollingConfig.class);
            String routKey = annotation.routKey().getRoutKey();
            pollingHandleMap.put(routKey,pollingHandle);
        }
//        CommonPollingConfigModel commonPollingConfigModel = CommonPollingConfigModel.builder()
//                .maxPolling(5)
//                .delayTime(2)
//                .routKey(RoutKeyEnum.TEST)
//                .sn(1)
//                .build();

        Integer [] integers = new Integer[4];
        integers[0] = 1;
        integers[1] = 2;
        integers[2] = 3;
        integers[3] = 1;

        Stuent stuent = Stuent.builder().name("ssss").build();
        CommonPollingConfigModel commonPollingConfigModel = CommonPollingConfigModel.builder()
                .routKey(RoutKeyEnum.TEST)
                .ifStep(true)
                .delayTimeArray(integers)
                .sendMsgJson(JSON.toJSONString(stuent))
                .build();
        send(commonPollingConfigModel);
    }

    public void send(CommonPollingConfigModel commonPollingConfigModel){
        if (ObjectUtils.isEmpty(commonPollingConfigModel.getSn())){
            commonPollingConfigModel.setSn(1);
        }
        Message message = new Message("test-topic-1", null, JSON.toJSONString(commonPollingConfigModel).getBytes());
        message.setDelayTimeLevel(getDelayTime(commonPollingConfigModel));
        try {
            rocketMqProducer.send(message);
            log.info("send message");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PollingHandle getPollingHandle(RoutKeyEnum routKey){

        return pollingHandleMap.get(routKey.getRoutKey());

    }

    private Integer getDelayTime(CommonPollingConfigModel commonPollingConfigModel){
        Integer delayTime = null;
        if (!ObjectUtils.isEmpty(commonPollingConfigModel.getIfStep())&&commonPollingConfigModel.getIfStep()){
            if (ObjectUtils.isEmpty(commonPollingConfigModel.getSn())){
                delayTime = commonPollingConfigModel.getDelayTimeArray()[0];
            }else {
                delayTime = commonPollingConfigModel.getDelayTimeArray()[commonPollingConfigModel.getSn()-1];
            }
        }else {
            delayTime = commonPollingConfigModel.getDelayTime();
        }
        return delayTime;
    }
}
