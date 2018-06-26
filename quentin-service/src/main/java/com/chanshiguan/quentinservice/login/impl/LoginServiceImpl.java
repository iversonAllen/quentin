package com.chanshiguan.quentinservice.login.impl;

import com.alibaba.fastjson.JSONObject;
import com.chanshiguan.quentincommon.constant.WxConstant;
import com.chanshiguan.quentincommon.http.HttpResponseModel;
import com.chanshiguan.quentincommon.http.HttpUtil;
import com.chanshiguan.quentinservice.login.LoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.wang
 * on 2018/6/26
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Override
    public Map<String, String> getOpenId(String code) {

        Map<String, String> map = new HashMap<>(2);

        //url
        String requestUrl = WxConstant.WX_URL.replace("APPID",WxConstant.APPID).replace("SECRET",WxConstant.SECRET).replace("JSCODE", code);

        // 发起http请求
        HttpResponseModel responseModel = HttpUtil.httpGet(requestUrl);
        if (responseModel == null) {
           return null;
        }

        JSONObject jsonObject = JSONObject.parseObject(responseModel.getBodyString());
        if(jsonObject == null){
            return null;
        }

        map.put("openid", jsonObject.getString("openid"));
        map.put("session_key", jsonObject.getString("session_key"));

        return map;
    }
}
