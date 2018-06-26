package com.chanshiguan.quentinbiz.login.impl;

import com.chanshiguan.quentinbiz.login.LoginBiz;
import com.chanshiguan.quentinservice.login.LoginService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
@Component
public class LoginBizImpl implements LoginBiz {

    @Resource
    private LoginService loginService;

    @Override
    public Map<String, String> getOpenId(String code) {

        return loginService.getOpenId(code);

    }
}
