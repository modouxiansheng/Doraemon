package com.example.springdemo.springdemo.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: pay
 * @description:
 * @author: hu_pf
 * @create: 2020-04-13 14:50
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyTransferRes {

    // 业务方编码
    private String businessCode;

    // 商户编号
    private  String  merchantCode;

    // 签名
    private String  sign;

    // 业务方转账单号
    private String merchantTransferNo;

    // 转账目标用户
    private String payeeId;

    // 校验用户姓名选项
    private String checkName;

    // 收款用户姓名
    private String payeeName;

    // 金额
    private Long totalFee;

    // 币种
    private String currency;

    // 企业付款备注
    private String descr;

    // 支付产品
    private Integer payProduct;

    // 状态
    private Integer status;

    // 支付平台生成的转账订单号
    private String transferNo;

    // 渠道订单号
    private String channelTransferNo;

    // 返回码和返回描述
    private String resultCode;

    // 返回码和返回描述
    private String resultCodeDes;

    // 创建时间
    private String createTime;

    // 完成时间
    private String completeTime;

}
