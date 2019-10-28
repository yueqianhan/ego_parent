package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/10/12 0012-下午 16:34
 */
public interface TbOrderDubboService
{
    /**
     * 新增数据
     * @param tbOrder
     * @param tbOrderItems
     * @param tbOrderShipping
     * @return
     */
    int insert(TbOrder tbOrder, List<TbOrderItem> tbOrderItems, TbOrderShipping tbOrderShipping) throws DaoException;
}
