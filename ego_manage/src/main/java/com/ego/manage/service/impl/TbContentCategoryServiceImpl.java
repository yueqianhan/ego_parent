package com.ego.manage.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 15:28
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService
{
    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboService;
    @Override
    public List<EasyUITreeNode> showCategory(Long pid)
    {
        List<TbContentCategory> list = tbContentCategoryDubboService.selectByPid(pid);

        List<EasyUITreeNode> listTree =new ArrayList<>();

        for (TbContentCategory category:list)
        {
            EasyUITreeNode node =new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent()?"closed":"open");
            listTree.add(node);
        }
        return listTree;
    }

    @Override
    public EgoResult insert(Long parentId, String name)
    {
        List<TbContentCategory> childNodes = tbContentCategoryDubboService.selectByPid(parentId);
        for (TbContentCategory category:childNodes)
        {
            if (name.equals(category.getName()))
            {
                return EgoResult.error("名称重复");
            }
        }
        Date date =new Date();
        TbContentCategory category =new TbContentCategory();
        category.setParentId(parentId);
        category.setName(name);
        category.setUpdated(date);
        category.setCreated(date);
        category.setIsParent(false);
        category.setStatus(1);
        category.setSortOrder(1);

        try
        {
            long result = tbContentCategoryDubboService.insert(category);

            if (result>0)
            {
                category.setId(result);
                return EgoResult.ok(category);
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
        return EgoResult.error("新增失败");
    }

    @Override
    public EgoResult update(Long id, String name)
    {
        TbContentCategory category = tbContentCategoryDubboService.selectCategoryById(id);
        List<TbContentCategory> list = tbContentCategoryDubboService.selectByPid(category.getParentId());
        for (TbContentCategory category2:list)
        {
            if (category2.getName().equals(name))
            {
                return EgoResult.error("名称不能重复");
            }
        }
        Date date =new Date();
        TbContentCategory category1 =new TbContentCategory();
        category1.setUpdated(date);
        category1.setName(name);
        category1.setId(id);
        int index = tbContentCategoryDubboService.update(category1);
        if (index==1)
        {
            return EgoResult.ok();
        }
        return EgoResult.error("修改失败");
    }

    @Override
    public EgoResult delete(Long id)
    {
        try
        {
            int index = tbContentCategoryDubboService.updateStatusById(id);
            if (index==1)
            {
               return EgoResult.ok();
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
        return EgoResult.error("删除失败");
    }
}
