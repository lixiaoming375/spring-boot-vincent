package com.vincent.config;

import com.vincent.intercept.PathPatternInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by gexiaobing on 2019-04-03
 *
 * @description TODO
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    List<PathPatternInterceptor> interceptorList;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(!CollectionUtils.isEmpty(interceptorList)){
            interceptorList.forEach(interceptor -> {
               registry.addInterceptor(interceptor).addPathPatterns(interceptor.getPathPattern());
            });
        }
    }
}
