package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 15:21
 */
public interface TbContentCategoryDubboService
{
    /**
     * 通过pid查询
     * @param pid
     * @return
     */
    List<TbContentCategory> selectByPid(Long pid);

    /**
     * 新增
      * @param category
     * @return
     */
    long insert(TbContentCategory category) throws DaoException;

    /**
     * 修改名称
     * @param tbContentCategory
     * @return
     */
    int update(TbContentCategory tbContentCategory);

    /**
     *根据id查询
     * @param id
     * @return
     */
    TbContentCategory selectCategoryById(Long id);

    /**
     * 修改status状态为2，即逻辑删除
     * @param id
     * @return
     */
    int updateStatusById(Long id) throws DaoException;
}
