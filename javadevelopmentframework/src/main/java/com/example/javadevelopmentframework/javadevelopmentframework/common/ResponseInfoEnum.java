package com.example.javadevelopmentframework.javadevelopmentframework.common;

public enum ResponseInfoEnum {

    SUCCESS(ResponseResult.OK,"处理成功"),
    PARAM_LENGTH_ERROR(ResponseResult.ERROR, "参数:%s,长度错误，max length: %s"),
    REQ_PARAM_ERROR(ResponseResult.ERROR, "请求报文必填参数%s缺失"),;

    private Integer code;
    private String message;

    ResponseInfoEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static void main(String[] args) {
        System.out.println(String.format(ResponseInfoEnum.REQ_PARAM_ERROR.getMessage(),"testValue"));
    }

}
