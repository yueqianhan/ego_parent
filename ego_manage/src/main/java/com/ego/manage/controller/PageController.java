package com.ego.manage.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 16:21
 */
@Controller
public class PageController
{
    /**
     * 显示页面控制器
     * @return
     */
    @RequestMapping("/")
    public String welcome()
    {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page)
    {
        return page;
    }

    @RequestMapping("/rest/page/item-edit")
    public String showItemUpdate()
    {
        return "item-edit";
    }
}
