package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 19:36
 */
@RestController
public class TbContentController
{
    @Autowired
    private TbContentService tbContentService;

    @GetMapping("/content/query/list")
    public EasyUIDataGrid showContent(Long categoryId,int page,int rows)
    {
        return tbContentService.selectByPage(categoryId,page,rows);
    }

    @PostMapping("/content/save")
    public EgoResult insert(TbContent tbContent)
    {
        return tbContentService.insert(tbContent);
    }

    @PostMapping("/rest/content/edit")
    public EgoResult update(TbContent tbContent)
    {
        return tbContentService.update(tbContent);
    }


    @PostMapping("/content/delete")
    public EgoResult delete(String  ids)
    {
        return tbContentService.delete(ids);
    }
}
