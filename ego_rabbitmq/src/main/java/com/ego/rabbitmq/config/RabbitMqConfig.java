package com.ego.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hanyueqian
 * @date 2019/9/28 0028-下午 18:39
 */
@Configuration
public class RabbitMqConfig
{
    @Bean
    public Queue createQueue()
    {
        return new Queue("adqueue");
    }

    @Bean
    public Queue createSolrQueue()
    {
        return new Queue("solrqueue");
    }

    @Bean
    public Queue itemUpdate()
    {
        return new Queue("itemUpdate");
    }

    @Bean
    public Queue orderQueue()
    {
        return  new Queue("orderQueue");
    }
}
