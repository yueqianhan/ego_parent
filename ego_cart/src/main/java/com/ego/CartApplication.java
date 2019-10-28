package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanyueqian
 * @date 2019/10/10 0010-下午 14:52
 */
@SpringBootApplication
@EnableDubbo
public class CartApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CartApplication.class,args);
    }
}
