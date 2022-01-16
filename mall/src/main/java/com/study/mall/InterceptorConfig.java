package com.study.mall;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yang
 * @date 2022/01/15 09:10
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserloginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/register","/categories");
    }
}