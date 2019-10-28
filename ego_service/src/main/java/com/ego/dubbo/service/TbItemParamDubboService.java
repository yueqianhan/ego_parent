package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 14:22
 */
public interface TbItemParamDubboService
{
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<TbItemParam> selectByPage(int pageNum,int pageSize);

    /**
     * 总条数
     * @return
     */
    Long selectCount();

    /**
     * 根据类目id，查询模板信息
     * @param catId
     * @return
     */
    TbItemParam selectByCatId(long catId);

    /**
     * 新增
     * @param tbItemParam
     * @return
     */
    int insert(TbItemParam tbItemParam);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    int deleteById(Long[] ids) throws DaoException;
}
