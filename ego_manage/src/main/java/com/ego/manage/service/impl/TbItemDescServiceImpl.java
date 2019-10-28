package com.ego.manage.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.manage.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 17:24
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService
{
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;
    @Override
    public EgoResult selectById(Long id)
    {
        TbItemDesc desc = tbItemDescDubboService.selectById(id);
        if (desc!=null)
        {
            return EgoResult.ok(desc);
        }
        return EgoResult.error("查询失败");
    }
}
