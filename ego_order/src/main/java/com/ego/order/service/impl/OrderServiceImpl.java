package com.ego.order.service.impl;

import com.ego.commons.pojo.OrderParam;
import com.ego.commons.utils.IDUtils;
import com.ego.order.service.OrderService;
import com.ego.pojo.TbOrderItem;
import com.ego.rabbitmq.sender.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/10/12 0012-下午 15:29
 */
@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    private RabbitMqSender rabbitMqSender;
    @Override
    public Map<String, Object> create(OrderParam orderParam)
    {
        long id = IDUtils.genItemId();
        orderParam.getOrderShipping().setOrderId(id+"");
        Date date =new Date();
        orderParam.getOrderShipping().setCreated(date);
        orderParam.getOrderShipping().setUpdated(date);

        for (TbOrderItem item : orderParam.getOrderItems())
        {
            item.setOrderId(id+"");
            item.setId(IDUtils.genItemId()+"");
        }
        rabbitMqSender.sendOrder(orderParam);
        return null;
    }
}
