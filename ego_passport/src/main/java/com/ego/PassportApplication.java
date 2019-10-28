package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanyueqian
 * @date 2019/10/9 0009-下午 15:27
 */
@SpringBootApplication
@EnableDubbo
public class PassportApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(PassportApplication.class,args);
    }
}
