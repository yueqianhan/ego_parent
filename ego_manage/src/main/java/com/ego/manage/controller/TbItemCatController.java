package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.manage.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 15:14
 */
@RestController
public class TbItemCatController
{

    @Autowired
    private TbItemCatService tbItemCatService;

    /**
     * 显示商品类目菜单项
     * @param id
     * @return
     */
    @RequestMapping("/item/cat/list")
    public List<EasyUITreeNode> showCat(@RequestParam(defaultValue = "0")Long id)
    {
        return tbItemCatService.selectByPid(id);
    }
}
