package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 16:15
 */
@SpringBootApplication
@EnableDubbo
public class ManageApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ManageApplication.class,args);
    }
}
