package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

/**
 * @author hanyueqian
 * @date 2019/10/9 0009-下午 15:46
 */
public interface PassportService
{
    /**
     * 用户登录
     * @param tbUser
     * @return
     */
    EgoResult login(TbUser tbUser);

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    EgoResult getUser(String token);


    /**
     * 用户退出
     * @param token
     * @return
     */
    EgoResult logout(String token);

    /**
     * 用户注册检查
     * @param param
     * @param type
     * @return
     */
    EgoResult check(String param,int type);

    /**
     * 注册
     * @param tbUser
     * @return
     */
    EgoResult register(TbUser tbUser);
}
