package com.chanshiguan.quentinapi.user;

import com.chanshiguan.quentinbiz.user.UserBiz;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by jie.wang
 * on 2018/6/21
 */
@RestController
@RequestMapping(path = "/api/user/")
public class UserController {

    @Resource
    private UserBiz userBiz;

    /**
     * 我的凭证
     */
    @RequestMapping(path = "test", produces = "application/json;charset=UTF-8")
    public String certificate(){

        return userBiz.test();
    }


}
