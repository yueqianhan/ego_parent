package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 14:24
 */
@Service
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService
{
    @Autowired
    private TbItemParamMapper tbItemParamMapper;
    @Override
    public List<TbItemParam> selectByPage(int pageNum, int pageSize)
    {
        /**
         * 设置分页条件
         */
        PageHelper.startPage(pageNum,pageSize);

        /**
         * 查询全部
         */
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(null);
        //获取分页数据
        PageInfo<TbItemParam> pi =new PageInfo<>(list);
        return pi.getList();
    }

    @Override
    public Long selectCount()
    {
        return tbItemParamMapper.countByExample(null);
    }

    @Override
    public TbItemParam selectByCatId(long catId)
    {
        TbItemParamExample example =new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if (list!=null && list.size()==1)
        {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insert(TbItemParam tbItemParam)
    {
        return tbItemParamMapper.insertSelective(tbItemParam);
    }

    @Override
    @Transactional
    public int deleteById(Long[] ids)  throws DaoException
    {
        int index =0;
        for (Long id:ids)
        {
           index += tbItemParamMapper.deleteByPrimaryKey(id);
        }
        if (index==ids.length)
        {
            return 1;
        }
        throw new DaoException("删除失败");
    }
}
