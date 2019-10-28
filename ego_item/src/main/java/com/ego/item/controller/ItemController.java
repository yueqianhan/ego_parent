package com.ego.item.controller;

import com.ego.item.pojo.CategoryResult;
import com.ego.item.pojo.PortalCategory;
import com.ego.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/27 0027-下午 16:43
 */
@Controller
public class ItemController
{
    @Autowired
    private ItemService itemService;

    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    @CrossOrigin
    public CategoryResult showCategory()
    {
        return itemService.showCategory();
    }

    @RequestMapping("/item/{id}.html")
    public String showDetail(@PathVariable Long id, Model model)
    {
        model.addAttribute("item",itemService.showItemDetails(id));
        return "item";
    }

    @RequestMapping("/item/desc/{id}.html")
    @ResponseBody
    public String showItemDesc(@PathVariable Long id)
    {
        return itemService.showItemDesc(id);
    }

    @RequestMapping("/item/param/{id}.html")
    @ResponseBody
    public String showParamDesc(@PathVariable Long id)
    {
        return itemService.showParams(id);
    }

}
