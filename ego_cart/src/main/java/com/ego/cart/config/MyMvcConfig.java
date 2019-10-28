package com.ego.cart.config;

import com.ego.cart.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hanyueqian
 * @date 2019/10/11 0011-下午 16:23
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer
{
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/cart/order-cart.html");
    }
}
