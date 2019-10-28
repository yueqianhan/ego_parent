package com.ego.manage.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanyueqian
 * @date 2019/9/24 0024-下午 16:47
 */
@RestController
public class TbItemParamItemController
{
    @Autowired
    private TbItemParamItemService tbItemParamItemService;

    /**
     * 显示商品规格参数信息
     * @param itemId
     * @return
     */
    @GetMapping("/rest/item/param/item/query/{itemId}")
    public EgoResult showItemParamItem(@PathVariable Long itemId)
    {
        return tbItemParamItemService.showItemParam(itemId);
    }
}
