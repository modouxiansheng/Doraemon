package com.example.transaction.transaction.mq.handler;


import com.example.transaction.transaction.mq.model.CommonPollingConfigModel;
import com.example.transaction.transaction.mq.model.CommonPollingConfigReturnModel;

public interface PollingHandle {

    CommonPollingConfigReturnModel invoke(CommonPollingConfigModel model);

}
