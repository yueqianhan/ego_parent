package com.ego.search.controller;

import com.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/9/30 0030-下午 15:18
 */
@Controller
public class SearchController
{

    @Autowired
    private SearchService searchService;

    @RequestMapping("search.html")
    public String showSearch(String q, @RequestParam(defaultValue = "1") int page, Model model)
    {
        Map<String, Object> query = searchService.query(q, page);
        model.addAllAttributes(query);
        return "search";
    }

    @RequestMapping("/solr/init")
    @ResponseBody
    public String init()
    {
        long start = System.currentTimeMillis();
        searchService.init();
        long stop = System.currentTimeMillis();
        return "用时"+(stop-start)/1000+"秒";
    }

    @PostMapping("/solr/insert")
    @ResponseBody
    public int insert(Long id)
    {
        return searchService.insert(id);
    }
}
