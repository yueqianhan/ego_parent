package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/10/12 0012-下午 16:36
 */
@Service
public class TbOrderDubboServiceImpl implements TbOrderDubboService
{
    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    @Transactional
    public int insert(TbOrder tbOrder, List<TbOrderItem> tbOrderItemList, TbOrderShipping tbOrderShipping) throws DaoException
    {
        boolean isEnough=true;

        for (TbOrderItem item:tbOrderItemList)
        {
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(Long.parseLong(item.getItemId()));
            if (tbItem.getNum()<item.getNum())
            {
                isEnough=false;
            }
        }
        if (isEnough)
        {
            tbOrder.setStatus(1);
        }else
        {
            tbOrder.setStatus(6);
            tbOrder.setCloseReason("商品库存不足");
            tbOrder.setCloseTime(new Date());
        }

        int index = tbOrderMapper.insertSelective(tbOrder);
        if (index==1)
        {
            int index2=0;
            for (TbOrderItem item:tbOrderItemList)
            {
               index2 += tbOrderItemMapper.insertSelective(item);
               if (isEnough)
               {
                   //修改商品数量
                   TbItem tbItem = tbItemMapper.selectByPrimaryKey(Long.parseLong(item.getItemId()));
                   tbItem.setNum(tbItem.getNum()-item.getNum());
                   tbItem.setUpdated(tbOrderShipping.getCreated());
                   int indexItem = tbItemMapper.updateByPrimaryKeySelective(tbItem);
                   if (indexItem!=1)
                   {
                       throw new DaoException("修改库存失败");
                   }
               }
            }
            if (index2==tbOrderItemList.size())
            {
                int index3 = tbOrderShippingMapper.insertSelective(tbOrderShipping);
                if (index3==1)
                {
                    return 1;
                }
            }
        }
        throw  new DaoException("新增失败");
    }
}
