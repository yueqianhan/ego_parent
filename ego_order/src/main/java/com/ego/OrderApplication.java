package com.ego;

import com.sun.org.apache.xpath.internal.Arg;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanyueqian
 * @date 2019/10/11 0011-下午 19:01
 */
@SpringBootApplication
@EnableDubbo
public class OrderApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(OrderApplication.class, args);
    }
}
