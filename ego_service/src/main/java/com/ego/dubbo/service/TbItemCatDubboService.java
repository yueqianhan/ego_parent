package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 14:54
 */
public interface TbItemCatDubboService
{
    /**
     * 根据父id查询所有正常子菜单的信息（删除文本除外）
     * @param parent_id 父菜单id
     * @return 所有子菜单
     */
    List<TbItemCat> selectByPid(Long parent_id);

    /**
     * 根据id(主键)查询
     * @param id
     * @return
     */
    TbItemCat selectById(Long id);
}
