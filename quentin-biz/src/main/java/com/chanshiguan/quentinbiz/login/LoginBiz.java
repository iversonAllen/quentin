package com.chanshiguan.quentinbiz.login;

import java.util.Map;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
public interface LoginBiz {

    /**
     * @DESC 获取opend、session_key
     * @param code 验证code
     */
    Map<String, String> getOpenId(String code);

}
