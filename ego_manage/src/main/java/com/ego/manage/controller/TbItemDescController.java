package com.ego.manage.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemDescService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 17:26
 */
@RestController
public class TbItemDescController
{
    @Autowired
    private TbItemDescService tbItemDescService;
    @GetMapping("/rest/item/query/item/desc/{id}")
    public EgoResult showDesc(@PathVariable Long id)
    {
        return tbItemDescService.selectById(id);
    }
}
