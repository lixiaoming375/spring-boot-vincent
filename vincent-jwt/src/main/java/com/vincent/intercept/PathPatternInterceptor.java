package com.vincent.intercept;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1917:13
 */
public interface PathPatternInterceptor extends HandlerInterceptor {
    /**
     * 获取拦截的url
     *
     * @return
     */
    String getPathPattern();
}
