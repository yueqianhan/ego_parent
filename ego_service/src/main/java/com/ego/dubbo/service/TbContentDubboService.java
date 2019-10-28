package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbContent;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 19:15
 */
public interface TbContentDubboService
{
    /**
     * 分页查询
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<TbContent> selectByPage(Long categoryId,int pageNum,int pageSize);

    /**
     * 根据id查询数据量
     * @param id
     * @return
     */
    long selectCount(Long categoryId);

    /**
     * 新增
     * @param tbContent
     * @return
     */
    int insert(TbContent tbContent);

    /**
     * 修改
     * @param id
     * @return
     */
    int update(TbContent tbContent);

    /**
     * 删除
     * @param ids
     * @return
     */
    int delete(Long[] ids) throws DaoException;

    List<TbContent> selectTopNByCategoryId(Long categoryId,int n);
}
