package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 15:23
 */
@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService
{
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<TbContentCategory> selectByPid(Long pid)
    {
        TbContentCategoryExample example =new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public long insert(TbContentCategory category) throws DaoException
    {
        int index = tbContentCategoryMapper.insertWithReturnId(category);
        if (index==1)
        {
            //新增成功后修改父节点的is_parent
            TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(category.getParentId());
            if (!parent.getIsParent())
            {
                TbContentCategory parentUpdate =new TbContentCategory();
                parentUpdate.setId(parent.getId());
                parentUpdate.setIsParent(true);
                parentUpdate.setUpdated(category.getCreated());
                int index2 = tbContentCategoryMapper.updateByPrimaryKeySelective(parentUpdate);
                if (index2!=1)
                {
                    throw new DaoException("修改父节点失败");
                }
            }

            return category.getId();
        }
        throw  new DaoException("新增失败");
    }

    @Override
    public int update(TbContentCategory tbContentCategory)
    {
        return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }

    @Override
    public TbContentCategory selectCategoryById(Long id)
    {
        return tbContentCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updateStatusById(Long id) throws DaoException
    {
        Date date =new Date();
        TbContentCategory category =new TbContentCategory();
        category.setId(id);
        category.setStatus(2);
        category.setUpdated(date);
        int index = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
        if (index==1)
        {
            //当前分类所有信息
            TbContentCategory currCategory =tbContentCategoryMapper.selectByPrimaryKey(id);

            TbContentCategoryExample example =new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(currCategory.getParentId()).andStatusEqualTo(1);
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
            if (list.size()==0)
            {
                TbContentCategory parent =new TbContentCategory();
                parent.setIsParent(false);
                parent.setUpdated(date);
                parent.setId(currCategory.getParentId());
                int index2 = tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
                if (index2==1)
                {
                    return 1;
                }else
                {
                    throw new DaoException("修改父节点失败");
                }
            }
            return 1;
        }
        throw new DaoException("修改当前节点失败");
    }
}
