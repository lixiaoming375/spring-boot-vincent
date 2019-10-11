package com.vincent.controller;

import com.alibaba.fastjson.JSON;
import com.vincent.dto.UserLoginReq;
import com.vincent.dto.UserLoginResp;
import com.vincent.utils.MUOContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1917:10
 */
@Controller
@RequestMapping("/do")
public class DoSomethingController {

    @GetMapping(value = "/something")
    @ResponseBody
    public String userLogin(){
        return "果然成功了 uid:"+MUOContext.getUid();
    }

}
