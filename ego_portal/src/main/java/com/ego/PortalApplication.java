package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanyueqian
 * @date 2019/9/27 0027-下午 15:00
 */
@SpringBootApplication
@EnableDubbo
public class PortalApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(PortalApplication.class,args);
    }
}
