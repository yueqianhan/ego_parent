package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 14:42
 */
public interface TbItemParamService
{
    /**
     * 显示商品模板的规格参数信息
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid showItemParam(int page,int rows);

    /**
     * 判断指定类目，是否有规格模板参数
     * @param catId
     * @return
     */
    EgoResult selectByParamByCatId(long catId);

    /**
     * 新增
     * @param catId
     * @param itemData
     * @return
     */
    EgoResult insert(long catId,String itemData);

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    EgoResult deleteByIds(String ids);
}
