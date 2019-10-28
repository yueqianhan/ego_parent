package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 17:18
 */
public interface TbItemDescDubboService
{

    /**
     * 根据id(主键)查询描述
     * @param id
     * @return
     */
    TbItemDesc selectById(Long id);
}
