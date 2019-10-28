package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 15:37
 */

@RestController
public class TbContentCategoryController
{
    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    @GetMapping("/content/category/list")
    public List<EasyUITreeNode> showCategory(@RequestParam(defaultValue = "0") Long id)
    {
        return tbContentCategoryService.showCategory(id);
    }

    @PostMapping("/content/category/create")
    public EgoResult insert(Long parentId,String  name)
    {
        return tbContentCategoryService.insert(parentId,name);
    }

    @PostMapping("/content/category/update")
    public EgoResult update(Long id,String name)
    {
        return tbContentCategoryService.update(id,name);
    }

    @PostMapping("/content/category/delete/")
    public EgoResult delete(Long id)
    {
        return tbContentCategoryService.delete(id);
    }
}
