package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 14:57
 */
@RestController
public class TbItemParamController
{
    @Autowired
    private TbItemParamService tbItemParamService;

    @GetMapping("/item/param/list")
    public EasyUIDataGrid showTbItemParam(int page,int rows)
    {
        return tbItemParamService.showItemParam(page,rows);
    }

    @GetMapping("/item/param/query/itemcatid/{catId}")
    public EgoResult getParamByCatId(@PathVariable long catId)
    {
        return tbItemParamService.selectByParamByCatId(catId);
    }

    @PostMapping("/item/param/save/{catId}")
    public EgoResult insert(@PathVariable  long catId,String paramData)
    {
        return tbItemParamService.insert(catId,paramData);
    }

    @PostMapping("/item/param/delete")
    public EgoResult delete(String ids)
    {
       return tbItemParamService.deleteByIds(ids);
    }

}
