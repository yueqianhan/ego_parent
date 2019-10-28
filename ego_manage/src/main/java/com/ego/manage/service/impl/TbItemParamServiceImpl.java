package com.ego.manage.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamCat;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 14:45
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService
{
    @Reference
    private TbItemParamDubboService tbItemParamDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Override
    public EasyUIDataGrid showItemParam(int page, int rows)
    {
        List<TbItemParam> list = tbItemParamDubboService.selectByPage(page, rows);
        List<TbItemParamCat> listCat =new ArrayList<>();
        for (TbItemParam param:list)
        {
            TbItemParamCat paramCat =new TbItemParamCat();
            BeanUtils.copyProperties(param,paramCat);
            paramCat.setItemCatName(tbItemCatDubboService.selectById(paramCat.getItemCatId()).getName());
            listCat.add(paramCat);
        }

        EasyUIDataGrid dataGrid =new EasyUIDataGrid();
        dataGrid.setRows(listCat);
        dataGrid.setTotal(tbItemParamDubboService.selectCount());
        return dataGrid;
    }

    @Override
    public EgoResult selectByParamByCatId(long catId)
    {
        TbItemParam tbItemParam = tbItemParamDubboService.selectByCatId(catId);
        return EgoResult.ok(tbItemParam);
    }

    @Override
    public EgoResult insert(long catId, String itemData)
    {

        Date date =new Date();
        TbItemParam tbItemParam =new TbItemParam();
        tbItemParam.setItemCatId(catId);
        tbItemParam.setParamData(itemData);
        tbItemParam.setUpdated(date);
        tbItemParam.setCreated(date);

        int index = tbItemParamDubboService.insert(tbItemParam);
        if (index==1)
        {
            return EgoResult.ok();
        }
        return EgoResult.error("新增失败");
    }

    @Override
    public EgoResult deleteByIds(String ids)
    {
        String[] idsArr = ids.split(",");
        Long[] idslong =new Long[idsArr.length];

        for (int i = 0; i < idsArr.length; i++)
        {
            idslong[i] = Long.parseLong(idsArr[i]);
        }
        try
        {
            int index = tbItemParamDubboService.deleteById(idslong);
            if (index==1)
            {
                return EgoResult.ok();
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
        return EgoResult.error("删除失败");
    }
}
