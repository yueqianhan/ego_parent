package com.ego.rabbitmq.sender;

import com.ego.commons.pojo.OrderParam;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hanyueqian
 * @date 2019/9/28 0028-下午 18:41
 */
@Component
public class RabbitMqSender
{
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String msg)
    {
        amqpTemplate.convertAndSend("adqueue",msg);
    }


    public void send(String routingKey,Long msg)
    {
        amqpTemplate.convertAndSend(routingKey,msg);
    }

    public void sendItemUpdate(Long id)
    {
        amqpTemplate.convertAndSend("itemUpdate",id);
    }


    public void sendOrder(OrderParam orderParam)
    {
        amqpTemplate.convertAndSend("orderQueue",orderParam);
    }
}
