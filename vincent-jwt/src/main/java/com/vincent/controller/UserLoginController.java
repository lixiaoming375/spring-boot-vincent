package com.vincent.controller;

import com.alibaba.fastjson.JSON;
import com.vincent.dto.UserLoginReq;
import com.vincent.dto.UserLoginResp;
import com.vincent.services.UserCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1915:33
 */
@Controller
@RequestMapping("/user")
public class UserLoginController {

    Logger log= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserCoreService userCoreService;

    @PostMapping(value = "/login")
    @NoCheckLogin
    public UserLoginResp userLogin(){
        UserLoginReq userLoginReq=new UserLoginReq();
        userLoginReq.setUserName("vincent");
        userLoginReq.setPassword("hehe");
        UserLoginResp userLoginResp=userCoreService.login(userLoginReq);
        log.info("userLoginResp:{}", JSON.toJSONString(userLoginResp));
        return  userLoginResp;
    }

}
