package com.ego.portal.service.impl;

import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.pojo.BigAd;
import com.ego.portal.service.PortalService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/28 0028-下午 16:37
 */
@Service
public class PortalServiceImpl implements PortalService
{
    @Value("${custom.bigad.id}")
    private Long bigAdId;

    @Value("${custom.bigad.num}")
    private int num;

    @Value("${custom.redis.bigAdKey}")
    private String redisAdKey;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Reference
    private TbContentDubboService tbContentDubboService;
    @Override
    public String showAd()
    {
        //判断是否有大广告缓存
        if (redisTemplate.hasKey(redisAdKey))
        {
            System.out.println("执行了缓存");
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            return redisTemplate.opsForValue().get(redisAdKey).toString();
        }

        List<TbContent> list = tbContentDubboService.selectTopNByCategoryId(bigAdId, num);

        List<BigAd> listAd =new ArrayList<>();

        for (TbContent content:list)
        {
            BigAd bigAd =new BigAd();
            bigAd.setAlt("");
            bigAd.setHeight(240);
            bigAd.setHeightB(240);
            bigAd.setHref(content.getUrl());
            bigAd.setSrc(content.getPic());
            bigAd.setSrcB(content.getPic2());
            bigAd.setWidth(670);
            bigAd.setWidthB(550);
            listAd.add(bigAd);
        }
        System.out.println("执行了数据库");
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.opsForValue().set(redisAdKey,listAd);
        return JsonUtils.objectToJson(listAd);
    }
}
