package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanyueqian
 * @date 2019/9/30 0030-下午 15:16
 */
@SpringBootApplication
@EnableDubbo
public class SearchApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SearchApplication.class,args);
    }
}
