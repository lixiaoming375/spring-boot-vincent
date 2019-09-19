package com.vincent.services.impl;

import com.vincent.dto.CheckAuthReq;
import com.vincent.dto.CheckAuthResp;
import com.vincent.dto.UserLoginReq;
import com.vincent.dto.UserLoginResp;
import com.vincent.services.UserCoreService;
import com.vincent.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1915:38
 */
@Service
public class UserCoreServiceImpl implements UserCoreService {

   Logger log= LoggerFactory.getLogger(this.getClass());

   @Override
   public UserLoginResp login(UserLoginReq request){
       log.info("login info:{}",request);
       UserLoginResp userLoginResp=new UserLoginResp();
       try{
           if(!request.getUserName().equals("vincent")||!request.getPassword().equals("hehe")){
               userLoginResp.setCode("1111");
               userLoginResp.setMsg("用户名密码错误");
           }
           Map<String,Object> map=new HashMap<>();
           map.put("uid",66);
//       map.put("exp",)
           userLoginResp.setToken(JwtTokenUtils.generatorToken(map));
           userLoginResp.setCode("0000");
           userLoginResp.setMsg("登陆成功");
       }catch (Exception ex){
           log.error(ex.getMessage(),ex);
       }
       return userLoginResp;
   }


   @Override
   public CheckAuthResp validToken(CheckAuthReq req){
       CheckAuthResp checkAuthResp=new CheckAuthResp();
       Claims claims=null;
       try{
            claims= JwtTokenUtils.phaseToken(req.getToken());
       }catch (ExpiredJwtException e){
           log.error("Expire:"+e);
           checkAuthResp.setCode("1112");
           checkAuthResp.setMsg("签名过期");
       }catch (SignatureException e1){
           log.error("SignatureException:"+e1);
           checkAuthResp.setCode("1113");
           checkAuthResp.setMsg("SignatureException");
       }catch (Exception e2){
           log.error("SignatureException:"+e2);
           checkAuthResp.setCode("1113");
           checkAuthResp.setMsg("SignatureException");
       }finally {
           log.info("response:"+checkAuthResp);
       }
       checkAuthResp.setUid(claims.get("uid").toString());
       checkAuthResp.setCode("0000");
       checkAuthResp.setMsg("验签成功");
       return checkAuthResp;
   }

}
