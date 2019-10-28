package com.ego.item.service;

import com.ego.item.pojo.CategoryResult;
import com.ego.item.pojo.TbItemChild;

/**
 * @author hanyueqian
 * @date 2019/9/27 0027-下午 16:23
 */
public interface ItemService
{
    CategoryResult showCategory();

    /**
     * 根据id显示商品详情
     * @param id
     * @return
     */
    TbItemChild showItemDetails(Long id);

    /**
     * 根据id显示商品详细数据
     * @param id
     * @return
     */
    String showItemDesc(Long id);

    /**
     * 根据id显示商品规格参数
     * @param itemId
     * @return
     */
    String showParams(Long itemId);
}
