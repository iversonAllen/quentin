package com.chanshiguan.quentinbiz.user.impl;

import com.chanshiguan.quentinbiz.user.UserBiz;
import com.chanshiguan.quentinservice.user.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by jie.wang
 * on 2018/6/21
 */
@Component
public class UserBizImpl implements UserBiz {

    @Resource
    private UserService userService;

    @Override
    public String test() {
        return userService.test();
    }
}
