package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 16:37
 */
public interface TbItemParamItemDubboService
{
    /**
     * 根据商品id查询商品规格参数信息
     * @param itemId
     * @return
     */
    TbItemParamItem selectByItemId(Long itemId);
}
