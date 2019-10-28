package com.ego.order.service;

import com.ego.commons.pojo.OrderParam;

import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/10/12 0012-下午 15:28
 */
public interface OrderService
{
    Map<String,Object> create(OrderParam orderParam);
}
