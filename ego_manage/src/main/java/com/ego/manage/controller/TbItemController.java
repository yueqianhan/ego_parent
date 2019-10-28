package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Contended;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 19:32
 */
@Controller
public class TbItemController
{
    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGrid showItem(int page,int rows)
    {
        return tbItemService.showItem(page,rows);
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public EgoResult delete(String ids)
    {
        return tbItemService.updateStatus((byte)3,ids);
    }


    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public EgoResult instock(String ids)
    {
        return tbItemService.updateStatus((byte)2,ids);
    }

    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(String ids)
    {
        return tbItemService.updateStatus((byte)1,ids);
    }

    @RequestMapping("/item/save")
    @ResponseBody
    public EgoResult insert(TbItem tbItem,String desc,String itemParams)
    {
        return tbItemService.insert(tbItem,desc,itemParams);
    }

    @PostMapping("/rest/item/update")
    @ResponseBody
    public EgoResult update(TbItem tbItem,String desc,Long itemParamId,String itemParams)
    {
        return tbItemService.update(tbItem,desc,itemParamId,itemParams);
    }

}
