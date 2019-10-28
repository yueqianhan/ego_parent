package com.ego.manage.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.manage.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 16:45
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService
{
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;
    @Override
    public EgoResult showItemParam(Long itemId)
    {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.selectByItemId(itemId);
        return EgoResult.ok(tbItemParamItem);
    }
}
