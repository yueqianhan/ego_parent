package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/26 0026-下午 19:18
 */
@Service
public class TbContentDubboServiceImpl implements TbContentDubboService
{
    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public List<TbContent> selectByPage(Long categoryId, int pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);

        TbContentExample example = new TbContentExample();
        if (categoryId!=0)
        {
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> tbContents = tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo =new PageInfo<>(tbContents);
        return pageInfo.getList();
    }

    @Override
    public long selectCount(Long categoryId)
    {
        TbContentExample example =new TbContentExample();
        if (categoryId!=0)
        {
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        return  tbContentMapper.countByExample(example);
    }

    @Override
    public int insert(TbContent tbContent)
    {
        return tbContentMapper.insertSelective(tbContent);
    }

    @Override
    public int update(TbContent tbContent)
    {
        return tbContentMapper.updateByPrimaryKeySelective(tbContent);
    }

    @Override
    public int delete(Long[]  ids) throws DaoException
    {
        int index=0;
        for (Long id:ids)
        {
            index += tbContentMapper.deleteByPrimaryKey(id);
        }
        if (index==ids.length)
        {
            return 1;
        }
        throw  new DaoException("删除失败");
    }

    @Override
    public List<TbContent> selectTopNByCategoryId(Long categoryId, int n)
    {
        PageHelper.startPage(1,n);
        TbContentExample example =new TbContentExample();
        example.setOrderByClause("updated desc");
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pi =new PageInfo<>(list);
        return pi.getList();
    }
}
