package com.ego.search.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.search.pojo.SearchEntity;
import com.ego.search.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/9/30 0030-下午 15:52
 */
@Service
public class SearchServiceImpl implements SearchService
{
    @Autowired
    private SolrOperations solrOperations;
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Value("${custom.solr.defaultCollection}")
    private String defaultCollection;

    @Value("${custom.search.num}")
    private int num;

    @Override
    public void init()
    {
        List<TbItem> list = tbItemDubboService.selectAll();

        for (TbItem item:list)
        {
            SolrInputDocument doc =new SolrInputDocument();
            doc.setField("id",item.getId()+"");
            doc.setField("item_title",item.getTitle());
            doc.setField("item_price",item.getPrice());
            doc.setField("item_image",item.getImage());
            doc.setField("item_sell_point",item.getSellPoint());
            //下面是get CCCCCCC id
            doc.setField("item_category_name",tbItemCatDubboService.selectById(item.getCid()).getName());
            doc.setField("item_desc",tbItemDescDubboService.selectById(item.getId()));
            solrOperations.saveBean(defaultCollection,doc);
        }
        solrOperations.commit(defaultCollection);
    }

    @Override
    public Map<String, Object> query(String q, int page)
    {
        List<SearchEntity> listResult =new ArrayList<>();
        SimpleHighlightQuery query =new SimpleHighlightQuery();
        //设置查询条件
        Criteria criteria =new Criteria("item_keywords");
        criteria.is(q);
        query.addCriteria(criteria);

        //设置分页
        query.setRows(num);
        query.setOffset(num*(page-1)*1L);

        //举例，按照上架时间进行排序。正常需要根据数据库中商品广告钱数，和商品优先级进行排序
        Sort sort =new Sort(Sort.Direction.DESC,"_version_");
        query.addSort(sort);

        //设置高亮
        HighlightOptions ops =new HighlightOptions();
        ops.addField("item_title item_sell_point");
        ops.setSimplePrefix("<span style='color:red'>");
        ops.setSimplePostfix("</span>");
        query.setHighlightOptions(ops);

        //执行查询
        HighlightPage<SearchEntity> highlightPage = solrOperations.queryForHighlightPage(defaultCollection, query, SearchEntity.class);
        List<HighlightEntry<SearchEntity>> highlighted = highlightPage.getHighlighted();
        for (HighlightEntry<SearchEntity> hlse:highlighted)
        {
            SearchEntity se =hlse.getEntity();
            List<HighlightEntry.Highlight> listHH = hlse.getHighlights();
            for (HighlightEntry.Highlight hh:listHH)
            {
                if (hh.getField().getName().equals("item_title"))
                {
                    se.setTitle(hh.getSnipplets().get(0));
                }

                if (hh.getField().getName().equals("item_sell_point"))
                {
                    se.setSellPoint(hh.getSnipplets().get(0));
                }
            }
            String image =se.getImage();
            se.setImages(image!=null&&!image.equals("")?image.split(","):new String[1]);
            listResult.add(se);
        }

        Map<String,Object> map =new HashMap<>();
        map.put("itemList",listResult);
        map.put("query",q);
        map.put("totalPages",highlightPage.getTotalPages());
        map.put("page",page);
        return map;
    }

    @Override
    public int insert(Long id)
    {
        TbItem item = tbItemDubboService.selectById(id);
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id",item.getId()+"");
        doc.setField("item_title",item.getTitle());
        doc.setField("item_price",item.getPrice());
        doc.setField("item_image",item.getImage());
        doc.setField("item_sell_point",item.getSellPoint());
        //下面是get CCCCCCC id
        doc.setField("item_category_name",tbItemCatDubboService.selectById(item.getCid()).getName());
        doc.setField("item_desc",tbItemDescDubboService.selectById(item.getId()));
        UpdateResponse ur = solrOperations.saveDocument(defaultCollection, doc);
        solrOperations.commit(defaultCollection);
        return ur.getStatus();
    }
}
