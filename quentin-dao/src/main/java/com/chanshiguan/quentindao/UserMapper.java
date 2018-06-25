package com.chanshiguan.quentindao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created by jie.wang
 * on 2018/6/22
 */
@Component
public interface UserMapper {

    @Select("select name from user_info where id = 1")
    String findByName();
}
