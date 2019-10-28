package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
 * @author hanyueqian
 * @date 2019/10/9 0009-下午 15:40
 */
public interface TbUserDubboService
{
    /**
     * 用户登录
     * @param tbUser
     * @return
     */
    TbUser login(TbUser tbUser);

    /**
     * 检查注册条件
     * @param tbUser
     * @return
     */
    Long selectByCount(TbUser tbUser);

    /**
     * 注册
     * @param tbUser
     * @return
     */
    int register(TbUser tbUser);
}
