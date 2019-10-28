package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 17:20
 */
@Service
public class TbItemDecDubboServiceImpl implements TbItemDescDubboService
{
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public TbItemDesc selectById(Long id)
    {
        return tbItemDescMapper.selectByPrimaryKey(id);
    }
}
