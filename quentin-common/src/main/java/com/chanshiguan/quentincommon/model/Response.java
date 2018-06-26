package com.chanshiguan.quentincommon.model;

import java.io.Serializable;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
public class Response implements Serializable{

    private static final long serialVersionUID = 3114413512742430917L;

    public static final Response OK = new Response();

    private int code;
    private String msg;
    private Object data;

    public Response() {
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public boolean equals(Object obj) {
        if (!(obj instanceof Response)) {
            return false;
        }
        return this.getCode() == ((Response) obj).getCode();
    }

    public int hashCode() {
        return this.code;
    }

    public boolean ok() {
        return this.code == 0;
    }

    public boolean notOk() {
        return this.code != 0;
    }
}
