package com.chanshiguan.quentinservice.user.impl;

import com.chanshiguan.quentindao.UserMapper;
import com.chanshiguan.quentinservice.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by jie.wang
 * on 2018/6/21
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String test() {

        return userMapper.findByName();
    }
}
