package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 16:47
 */
@SpringBootApplication
@MapperScan("com.ego.mapper")
@EnableDubbo
public class DubboApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DubboApplication.class,args);
    }
}
