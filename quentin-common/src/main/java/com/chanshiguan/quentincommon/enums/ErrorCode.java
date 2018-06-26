package com.chanshiguan.quentincommon.enums;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
public enum ErrorCode {

    SUCCESS(0, "success"),

    //参数错误 100001 ~ 19999
    REQUEST_PARAM_EMPRY(10001, "请求参数错误"),


    DEFAULT(99999, "NULL");

    private Integer code;

    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
