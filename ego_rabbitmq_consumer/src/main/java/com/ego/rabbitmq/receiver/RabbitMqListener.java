package com.ego.rabbitmq.receiver;

import com.ego.commons.pojo.OrderParam;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.pojo.TbOrder;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/9/28 0028-下午 18:54
 */
@Component
public class RabbitMqListener
{

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Value("${custom.rabbitmq.solr.host}")
    private String solrHost;
    @Value("${custom.rabbitmq.solr.insert}")
    private String insert;

    @Value("${custom.redis.item-detail}")
    private String detailKey;

    @Reference
    private TbOrderDubboService tbOrderDubboService;
    /**
     * 传递过来的消息就是要删除redis中的key
     * @param key
     */
    @RabbitListener(queues = "adqueue")
    public void receiver(String key)
    {
        redisTemplate.delete(key);
    }

    @RabbitListener(queues = "solrqueue")
    public void receiverSolr(Long msg)
    {
        Map<String,String> map =new HashMap<>();
        map.put("id",msg+"");
        String s = HttpClientUtil.doPost(solrHost + insert, map);
        System.out.println(s);
    }

    @RabbitListener(queues = "itemUpdate")
    public void itemUpdate(Long id)
    {
        //solr数据同步
        Map<String,String> map =new HashMap<>();
        map.put("id",id+"");
        String s = HttpClientUtil.doPost(solrHost + insert, map);
        //redis数据同步,把修改完的数据同步到redis
        String key = detailKey+":"+id;
        redisTemplate.delete(key);
    }


    @RabbitListener(queues = "orderQueue")
    public void orderCreate(OrderParam orderParam)
    {
        System.out.println("OrderParam:"+orderParam);

        TbOrder tbOrder =new TbOrder();
        tbOrder.setPayment(orderParam.getPayment());
        tbOrder.setPaymentType(orderParam.getPaymentType());
        tbOrder.setOrderId(orderParam.getOrderShipping().getOrderId());
        int index = tbOrderDubboService.insert(tbOrder, orderParam.getOrderItems(), orderParam.getOrderShipping());

    }
}
