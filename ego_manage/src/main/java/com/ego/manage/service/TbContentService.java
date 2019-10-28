package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 19:29
 */
public interface TbContentService
{

    EasyUIDataGrid selectByPage(Long categoryId,int page,int rows);

    /**
     * 新增
     * @param tbContent
     * @return
     */
    EgoResult insert(TbContent tbContent);

    /**
     * 修改
     * @param tbContent
     * @return
     */
    EgoResult update(TbContent tbContent);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    EgoResult delete(String ids);

}
