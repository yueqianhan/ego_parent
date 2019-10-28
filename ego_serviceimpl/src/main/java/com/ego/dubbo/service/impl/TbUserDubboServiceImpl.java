package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/10/9 0009-下午 15:41
 */
@Service
public class TbUserDubboServiceImpl implements TbUserDubboService
{
    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public TbUser login(TbUser tbUser)
    {
        TbUserExample example =new TbUserExample();
        example.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (tbUsers!=null&&tbUsers.size()==1)
        {
            return tbUsers.get(0);
        }
        return null;
    }

    @Override
    public Long selectByCount(TbUser tbUser)
    {
        TbUserExample example =new TbUserExample();
        TbUserExample.Criteria c = example.createCriteria();
        if (tbUser.getUsername()!=null&&!tbUser.getUsername().equals(""))
        {
            c.andUsernameEqualTo(tbUser.getUsername());
        }
        if (tbUser.getPhone()!=null&&!tbUser.getPhone().equals(""))
        {
            c.andPhoneEqualTo(tbUser.getPhone());
        }

        if (tbUser.getEmail()!=null&&!tbUser.getEmail().equals(""))
        {
            c.andEmailEqualTo(tbUser.getEmail());
        }
        return tbUserMapper.countByExample(example);
    }

    @Override
    public int register(TbUser tbUser)
    {
        return tbUserMapper.insertSelective(tbUser);
    }
}
