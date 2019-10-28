package com.ego.manage.service;

import com.ego.commons.pojo.EgoResult;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 17:22
 */
public interface TbItemDescService
{
    /**
     * 根据主键查询，返回值要包含TbItemDesc对象
     * @param id
     * @return
     */
    EgoResult selectById(Long id);
}
