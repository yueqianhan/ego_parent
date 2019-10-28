package com.ego.manage.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.rabbitmq.sender.RabbitMqSender;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 19:30
 */
@Service
public class TbContentServiceImpl implements TbContentService
{
    @Reference
    private TbContentDubboService tbContentDubboService;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Value("${custom.redis.bigAdKey}")
    private String adKey;
    @Value("${custom.bigad.id}")
    private Long adCategoryId;
    @Override
    public EasyUIDataGrid selectByPage(Long categoryId, int page, int rows)
    {
        List<TbContent> tbContents = tbContentDubboService.selectByPage(categoryId, page, rows);
        EasyUIDataGrid dataGrid =new EasyUIDataGrid();
        dataGrid.setTotal(tbContentDubboService.selectCount(categoryId));
        dataGrid.setRows(tbContents);
        return dataGrid;

    }

    @Override
    public EgoResult insert(TbContent tbContent)
    {
        Date date =new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        int index = tbContentDubboService.insert(tbContent);
        if (index==1)
        {
            if(tbContent.getCategoryId().equals(adCategoryId)) {
                //使用rabbitmq发送一个异步消息。
                //不影响原有代码执行性能。
                rabbitMqSender.send(adKey);
            }
            return  EgoResult.ok();
        }
        return EgoResult.error("新增失败");
    }

    @Override
    public EgoResult update(TbContent tbContent)
    {
        Date date =new Date();
        tbContent.setUpdated(date);
        int index = tbContentDubboService.update(tbContent);
        if (index==1)
        {
            return EgoResult.ok(tbContent);
        }
        return EgoResult.error("修改失败");
    }

    @Override
    public EgoResult delete(String ids)
    {
        String[] idsArr = ids.split(",");
        Long[] idsLong =new Long[idsArr.length];
        for (int i = 0; i < idsArr.length; i++)
        {
            idsLong[i] =Long.parseLong(idsArr[i]);
        }
        int index = tbContentDubboService.delete(idsLong);
        if (index==1)
        {
            return EgoResult.ok();
        }
        return EgoResult.error("删除失败");
    }
}
