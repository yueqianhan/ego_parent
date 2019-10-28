package com.ego.search.service;

import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/9/30 0030-下午 15:51
 */
public interface SearchService
{
    /**
     * 初始化数据
     */
    void init();

    /**
     * 商品查询，并分页显示
     * @param q
     * @param page
     * @return
     */
    Map<String,Object> query(String q,int page);

    /**
     * 新增
     * @param id
     * @return
     */
    int insert(Long id);
}
