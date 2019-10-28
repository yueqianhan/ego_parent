package com.ego.item.service.impl;

import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.*;
import com.ego.item.pojo.CategoryResult;
import com.ego.item.pojo.ItemParam;
import com.ego.item.pojo.PortalCategory;
import com.ego.item.pojo.TbItemChild;
import com.ego.item.service.ItemService;
import com.ego.pojo.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/27 0027-下午 16:24
 */
@Service
public class ItemServiceImpl implements ItemService
{
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Value("${custom.redis.portalNavKey}")
    private String key;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Reference
    private TbItemDubboService tbItemDubboService;

    @Value("${custom.redis.item-detail}")
    private String detailKey;

    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;

    @Override
    public CategoryResult showCategory()
    {
        //判断缓存是否存在
        if (redisTemplate.hasKey(key))
        {
            System.out.println("缓存");
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<CategoryResult>(CategoryResult.class));
            return (CategoryResult) redisTemplate.opsForValue().get(key);
        }

        //没有缓存
        CategoryResult cr =new CategoryResult();
        List<PortalCategory> list =new ArrayList<>();

        //填充
        List<TbItemCat> listFirst = tbItemCatDubboService.selectByPid(0L);
        for (TbItemCat cat:listFirst)
        {
            PortalCategory category =new PortalCategory();
            category.setU("/products/"+cat.getId()+".html");
            category.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
            List<TbItemCat> listChildren = tbItemCatDubboService.selectByPid(cat.getId());
            List<PortalCategory> listChildrenCategory =new ArrayList<>();
            for (TbItemCat child:listChildren)
            {
                PortalCategory categoryChild = new PortalCategory();
                categoryChild.setU("/products/"+child.getId()+".html");
                categoryChild.setN(child.getName());
                List<String> listSecond =new ArrayList<>();
                List<TbItemCat> listThird = tbItemCatDubboService.selectByPid(child.getId());
                for (TbItemCat third:listThird)
                {
                    listSecond.add("/products/"+third.getId()+".html|"+third.getName());
                }
                categoryChild.setI(listSecond);
                listChildrenCategory.add(categoryChild);
            }
            category.setI(listChildrenCategory);
            list.add(category);
        }
        cr.setData(list);
        redisTemplate.opsForValue().set(key,cr);
        System.out.println("调用mysql");
        return cr;
    }

    @Override
    public TbItemChild showItemDetails(Long id)
    {
        String key =detailKey+":"+id;
        if (redisTemplate.hasKey(key))
        {
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbItemChild>(TbItemChild.class));
            System.out.println("商品详情-缓存");
            return (TbItemChild) redisTemplate.opsForValue().get(key);
        }
        TbItem tbItem =tbItemDubboService.selectById(id);
        TbItemChild tbItemChild =new TbItemChild();
        BeanUtils.copyProperties(tbItem,tbItemChild);
        String image =tbItem.getImage();
        tbItemChild.setImages(image!=null&&!image.equals("")?image.split(","):new String[1]);
        redisTemplate.opsForValue().set(key,tbItemChild);
        System.out.println("商品详情-mysql");
        return tbItemChild;
    }

    @Override
    public String showItemDesc(Long id)
    {
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(id);
        return tbItemDesc.getItemDesc();
    }

    @Override
    public String showParams(Long itemId)
    {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.selectByItemId(itemId);
        String paramData = tbItemParamItem.getParamData();
        List<ItemParam> list = JsonUtils.jsonToList(paramData, ItemParam.class);
        StringBuilder sf =new StringBuilder();
        for (ItemParam param:list)
        {
            sf.append("<table style='color:gray;'>");
            for(int i=0;i<param.getParams().size();i++)
            {
                sf.append("<tr>");
                if (i==0)
                {
                    sf.append("<td>"+param.getGroup()+"</td>");
                }else
                {
                    sf.append("<td></td>");
                }
                sf.append("<td>"+param.getParams().get(i).getK()+"</td>");
                sf.append("<td>"+param.getParams().get(i).getV()+"</td>");
                sf.append("</tr>");
            }
            sf.append("</table>");
            sf.append("<hr style='color:gray;'/>");
        }
        return sf.toString();
    }
}
