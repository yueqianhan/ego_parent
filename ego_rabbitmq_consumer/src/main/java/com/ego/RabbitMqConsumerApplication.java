package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanyueqian
 * @date 2019/9/28 0028-下午 18:52
 */
@SpringBootApplication
@EnableDubbo
public class RabbitMqConsumerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RabbitMqConsumerApplication.class,args);
    }
}
