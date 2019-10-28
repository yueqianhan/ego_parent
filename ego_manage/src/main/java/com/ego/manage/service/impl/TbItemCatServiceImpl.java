package com.ego.manage.service.impl;

import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 15:07
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService
{
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    /**
     * 根据父id查询商品类目信息
     * @param pid
     * @return
     */
    @Override
    public List<EasyUITreeNode> selectByPid(Long pid)
    {
        List<TbItemCat> list = tbItemCatDubboService.selectByPid(pid);
        List<EasyUITreeNode> listTree = new ArrayList<>();
        for (TbItemCat cat:list)
        {
            EasyUITreeNode node =new EasyUITreeNode();
            node.setId(cat.getId());
            node.setText(cat.getName());
            node.setState(cat.getIsParent()?"closed":"open");
            //一定要添加(别忘记)
            listTree.add(node);
        }
        return listTree;
    }
}
