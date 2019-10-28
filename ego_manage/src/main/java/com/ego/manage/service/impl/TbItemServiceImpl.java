package com.ego.manage.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.rabbitmq.sender.RabbitMqSender;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 19:32
 */
@Service
public class TbItemServiceImpl implements TbItemService
{
    @Reference
    private TbItemDubboService tbItemDubboService;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Value("${custom.rabbitmq.solr}")
    private String solrQueue;

    @Override
    public EasyUIDataGrid showItem(int page, int rows)
    {
        EasyUIDataGrid easyUIDataGrid =new EasyUIDataGrid();
        easyUIDataGrid.setTotal(tbItemDubboService.selectCount());
        easyUIDataGrid.setRows(tbItemDubboService.selectByPage(page,rows));
        return easyUIDataGrid;
    }

    @Override
    public EgoResult updateStatus(Byte status, String ids)
    {
        String [] strArr =ids.split(",");
        Long[] longArr =new Long[strArr.length];
        for (int i = 0; i <strArr.length ; i++)
        {
            longArr[i]=Long.parseLong(strArr[i]);
        }
        try
        {
            int index = tbItemDubboService.updateStatusByIds(status, longArr);
            if (index==1)
            {
                return EgoResult.ok();
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
        return EgoResult.error("操作失败");
    }

    @Override
    public EgoResult insert(TbItem tbItem, String desc,String itemParams)
    {
        Date date =new Date();
        long id = IDUtils.genItemId();
        //tb_item表数据
        tbItem.setId(id);
        tbItem.setStatus((byte)1);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);

        //tb_item_desc表数据
        TbItemDesc tbItemDesc =new TbItemDesc();
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(desc);


        TbItemParamItem tbItemParamItem =new TbItemParamItem();
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setParamData(itemParams);


        String errorMsg="";
        try
        {
            int index = tbItemDubboService.insert(tbItem, tbItemDesc,tbItemParamItem);
            if (index==1)
            {
                //同步solr中数据
                //调用ego_search项目的/solr/insert控制器，并传递参数就可以同步数据。
                rabbitMqSender.send(solrQueue,id);
                return EgoResult.ok();
            }

        } catch (DaoException e)
        {
            e.printStackTrace();
            errorMsg =e.getMessage();
        }
        return EgoResult.error(errorMsg);
    }

    @Override
    public EgoResult update(TbItem tbItem, String desc,Long itemParamId,String itemParams)
    {
        Date date =new Date();
        //item数据补全
        tbItem.setUpdated(date);

        //item_desc数据补全
        TbItemDesc tbItemDesc =new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(desc);

        //tb_item_param_item数据补全
        TbItemParamItem tbItemParamItem =new TbItemParamItem();
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setId(itemParamId);
        tbItemParamItem.setUpdated(date);

        try
        {
            int index = tbItemDubboService.update(tbItem, tbItemDesc,tbItemParamItem);
            if (index==1)
            {
                //数据同步
                //solr数据同步
                //redis-详情数据同步
                rabbitMqSender.sendItemUpdate(tbItem.getId());
                return EgoResult.ok();
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
        return EgoResult.error("修改失败");
    }
}
