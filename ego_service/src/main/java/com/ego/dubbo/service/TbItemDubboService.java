package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 18:50
 */
public interface TbItemDubboService
{
    /**
     * 分页查询
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<TbItem>  selectByPage(int pageNum,int pageSize);

    /**
     * 总条数
     * @return
     */
    Long selectCount();

    /**
     * 批量更新，商品状态
     * @param status
     * @param ids
     * @return
     */
    int updateStatusByIds(Byte status,Long[] ids) throws DaoException;

    /**
     * 新增
     * @param tbItem 商品
     * @param tbItemDesc 商品描述
     * @return 返回值为1表示成功
     */
    int insert(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws DaoException;


    /**
     * 修改
     * @param tbItem
     * @param tbItemDesc
     * @return返回值为1表示成功
     * @throws DaoException
     */
    int update(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem itemParamItem) throws DaoException;


    List<TbItem> selectAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    TbItem selectById(Long id);
}
