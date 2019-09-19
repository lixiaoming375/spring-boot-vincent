package com.vincent.intercept;

import com.vincent.controller.NoCheckLogin;
import com.vincent.dto.CheckAuthReq;
import com.vincent.dto.CheckAuthResp;
import com.vincent.services.UserCoreService;
import com.vincent.utils.MUOContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1917:17
 */
@Component
public class LoginInterceptor implements PathPatternInterceptor {

    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private UserCoreService userCoreService;

    @Override
    public String getPathPattern() {
        return "/**";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MUOContext.clean();
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod=(HandlerMethod)handler;
            if(isNotCheckLogin(handlerMethod)){
                return true;
            }
            String accessToken = request.getHeader("accessToken");
            if(!StringUtils.isEmpty(accessToken)){
                CheckAuthReq checkAuthReq= new CheckAuthReq();
                checkAuthReq.setToken(accessToken);
                CheckAuthResp resp=userCoreService.validToken(checkAuthReq);
                if(resp.getCode().equals("0000")){
                    MUOContext.setUid(resp.getUid());
                }else{
                    writeErrorMsg(response,"1006","无效token");
                    return false;
                }

            }else{
                writeErrorMsg(response,"1000","会员没登录");
                return false;
            }
        }
        return true;
    }

    private boolean isNotCheckLogin(HandlerMethod handlerMethod){
        Object bean=handlerMethod.getBean();
        Class clazz= bean.getClass();
        if(clazz.getAnnotation(NoCheckLogin.class)!=null){
            return true;
        }
        Method method=handlerMethod.getMethod();
        return method.getAnnotation(NoCheckLogin.class)!=null;
    }

    /**
     * 写错误信息
     * @param response
     */
    private void writeErrorMsg(HttpServletResponse response,String code,String message){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "*");
        ServletOutputStream out;
        try {
            out = response.getOutputStream();
            out.write("{'code':'-1','msg':'error'}".getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            logger.warn(e.getMessage(),e);
        }
    }

}
