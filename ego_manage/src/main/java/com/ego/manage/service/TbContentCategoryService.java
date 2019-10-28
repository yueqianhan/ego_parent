package com.ego.manage.service;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.commons.pojo.EgoResult;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 15:27
 */
public interface TbContentCategoryService
{

    /**
     * 显示树状菜单
     * @param pid
     * @return
     */
    List<EasyUITreeNode> showCategory(Long pid);

    /**
     * 新增
     * @param parentId
     * @param name
     * @return
     */
    EgoResult insert(Long parentId,String name);

    /**
     * 修改名称
     * @param id
     * @param name
     * @return
     */
    EgoResult update(Long id,String name);

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    EgoResult delete(Long id);
}
