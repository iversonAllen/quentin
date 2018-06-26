package com.chanshiguan.quentincommon.model;

import com.alibaba.fastjson.JSON;
import com.chanshiguan.quentincommon.enums.ErrorCode;
import com.google.gson.GsonBuilder;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
public class ResponseUtil {

    public static String toJson(int code, String msg, Object obj) {
        Response result = new Response();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj);

        return new GsonBuilder().disableHtmlEscaping().create().toJson(result);
    }

    public static String makeErrorResponseJson(ErrorCode errorCode) {
        Response errorResponse = makeError(errorCode);
        return makeResponseJson(errorResponse);
    }

    public static Response makeFail(String message) {
        return makeResponse(1, message, null);
    }

    public static String makeResponseJson(Response response) {
        return new GsonBuilder().disableHtmlEscaping().create().toJson(response);
    }

    @Deprecated
    public static String success(Object obj) {
        return toJson(0, "", obj);
    }

    public static Response makeSuccess(Object obj) {
        return makeResponse(0, "", obj);
    }

    public static Response makeSuccess(Object obj, String msg) {
        return makeResponse(0, msg, obj);
    }

    public static Response makeFail(Object obj) {
        return makeResponse(1, "", obj);
    }

    public static Response makeError(ErrorCode errorCode) {
        return makeResponse(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public static Response makeError(ErrorCode errorCode, Object obj) {
        return makeResponse(errorCode.getCode(), errorCode.getMsg(), obj);
    }

    public static Response makeAdminError(ErrorCode errorCode) {
        return makeResponse(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public static Response makeAdminError(ErrorCode errorCode, Object obj) {
        return makeResponse(errorCode.getCode(), errorCode.getMsg(), obj);
    }

    public static Response makeResponse(int code, String msg, Object obj) {
        Response result = new Response();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj);

        return result;
    }

    public static boolean isOk(Response response) {
        if (response != null && response.getCode() == 0) {
            return true;
        }
        return false;
    }

}
