package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 16:39
 */
@Service
public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService
{
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Override
    public TbItemParamItem selectByItemId(Long itemId)
    {
        TbItemParamItemExample example =new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list!=null && list.size()==1)
        {
            return list.get(0);
        }
        return null;
    }
}
