package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 19:30
 */
public interface TbItemService
{
    EasyUIDataGrid showItem(int page,int rows);

    EgoResult updateStatus(Byte status,String ids);

    /**
     * 新增
     * @param tbItem 商品
     * @param desc 商品描述
     * @return
     */
    EgoResult insert(TbItem tbItem,String desc,String itemParams);

    /**
     * 修改
     * @param tbItem 商品
     * @param desc 商品描述
     * @return
     */
    EgoResult update(TbItem tbItem,String desc,Long itemParamId,String itemParams);

}
