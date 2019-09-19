package com.vincent.controller;

import com.alibaba.fastjson.JSON;
import com.vincent.dto.UserLoginReq;
import com.vincent.dto.UserLoginResp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1917:10
 */
@Controller
@RequestMapping("/do")
public class DoSomethingController {

    @GetMapping(value = "/something")
    public void userLogin(){
        System.out.println("果然成功了");
    }

}
