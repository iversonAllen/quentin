package com.chanshiguan.quentinapi.login;

import com.chanshiguan.quentinbiz.login.LoginBiz;
import com.chanshiguan.quentincommon.enums.ErrorCode;
import com.chanshiguan.quentincommon.model.Response;
import com.chanshiguan.quentincommon.model.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * Created by jie.wang
 * on 2018/6/26
 */
@Controller
@RequestMapping(path = "/api/login")
public class LoginController {

    @Resource
    private LoginBiz loginBiz;

    /**
     * 小程序登录验证
     */
    @ResponseBody
    @RequestMapping(value="/getOpenId",method = RequestMethod.POST)
    public Response getOpenId(@RequestParam("code") String code){

        if(StringUtils.isEmpty(code)){
            ResponseUtil.makeError(ErrorCode.REQUEST_PARAM_EMPRY);
        }

        return ResponseUtil.makeSuccess(loginBiz.getOpenId(code));
    }
}
