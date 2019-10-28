package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.pojo.TbItemParam;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 14:56
 */
@Service
public class TbItemCatDubboServiceImpl implements TbItemCatDubboService
{
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<TbItemCat> selectByPid(Long parent_id)
    {
        //封装sql中where从句，只要sql中有where从句就有example(除了根据id查询)
        TbItemCatExample example =new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(parent_id).andStatusEqualTo(1);
        return tbItemCatMapper.selectByExample(example);
    }

    @Override
    public TbItemCat selectById(Long id)
    {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }
}
