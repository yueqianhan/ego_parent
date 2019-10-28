package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 18:56
 */
@Service
public class TbItemDubboServiceImpl implements TbItemDubboService
{
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Override
    public List<TbItem> selectByPage(int pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum,pageSize);
        List<TbItem> list = tbItemMapper.selectByExample(null);
        // 构造方法参数必须是查询全部的结果。否则无法知道给哪个sql后面拼接limit
        //PageInfo是分页查询所有查询结果封装的类，所有的结果都从这个类取
        PageInfo<TbItem> pi =new PageInfo<>(list);
        //查询全部
        return pi.getList();
    }

    @Override
    public Long selectCount()
    {
        return tbItemMapper.countByExample(null);
    }

    @Override
    @Transactional
    public int updateStatusByIds(Byte status, Long[] ids) throws DaoException
    {
        Date date =new Date();
        int index=0;
        for (Long id:ids)
        {
            TbItem tbItem =new TbItem();
            tbItem.setId(id);
            tbItem.setStatus(status);
            tbItem.setUpdated(date);
            index+=tbItemMapper.updateByPrimaryKeySelective(tbItem);
        }
        if (index==ids.length)
        {
            return 1;
        }
        throw new DaoException("批量更新失败");
    }

    @Override
    @Transactional
    public int insert(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws DaoException
    {
        int index = tbItemMapper.insert(tbItem);
        if (index==1)
        {
            int index2 = tbItemDescMapper.insert(tbItemDesc);
            if (index2==1)
            {
                int index3 = tbItemParamItemMapper.insertSelective(tbItemParamItem);
                if (index3==1)
                {
                    return 1;
                }
            }
        }
        throw new DaoException("新增失败");
    }

    @Override
    @Transactional
    public int update(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem itemParamItem) throws DaoException
    {
        int index = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        if (index==1)
        {
            int index2 = tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
            if (index2==1)
            {
                int index3 = tbItemParamItemMapper.updateByPrimaryKeySelective(itemParamItem);
                if (index3==1)
                {
                    return 1;
                }
            }
        }
        throw  new DaoException("修改失败");
    }

    @Override
    public List<TbItem> selectAll()
    {
        return tbItemMapper.selectByExample(null);
    }

    @Override
    public TbItem selectById(Long id)
    {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}
