package com.ego.order.controller;

import com.ego.commons.pojo.OrderParam;
import com.ego.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hanyueqian
 * @date 2019/10/11 0011-下午 18:58
 */
@Controller
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create.html")
    public String showOrder(OrderParam orderParam)
    {
        System.out.println("OrderParam:"+orderParam);
        orderService.create(orderParam);
        return "success";
    }
}
