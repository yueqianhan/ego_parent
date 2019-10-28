package com.ego.passport.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.PassportService;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * @author hanyueqian
 * @date 2019/10/9 0009-下午 15:47
 */
@Service
public class PassportServiceImpl implements PassportService
{
    @Reference
    private TbUserDubboService tbUserDubboService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public EgoResult login(TbUser tbUser)
    {
        String password = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(password);
        TbUser user = tbUserDubboService.login(tbUser);
        if (user != null)
        {
            //1.生成UUID(token)
            String token = UUID.randomUUID().toString();
            //2.把token当做key，user当做value存储在redis中
            user.setPassword("");
            redisTemplate.opsForValue().set(token, user, Duration.ofSeconds(1209600L));
            //3.把token放入到cookie中
            //通过第四个参数控制多长时间免登录
            CookieUtils.setCookie(ServletUtil.getRequest(), ServletUtil.getResponse(), "TT_TOKEN", token, 1209600);
            return EgoResult.ok("");
        }
        return EgoResult.error("登录失败");
    }

    @Override
    public EgoResult getUser(String token)
    {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
        TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(token);
        if (tbUser != null)
        {
            return EgoResult.ok(tbUser);
        }
        return EgoResult.error("用户未登录");
    }

    @Override
    public EgoResult logout(String token)
    {
        Boolean result = redisTemplate.delete(token);
        CookieUtils.deleteCookie(ServletUtil.getRequest(), ServletUtil.getResponse(), token);
        if (result)
        {
            return EgoResult.ok();
        }
        return EgoResult.error("用户退出-删除失败");
    }

    @Override
    public EgoResult check(String param, int type)
    {
        TbUser tbUser = new TbUser();
        if (type == 1)
        {
            tbUser.setUsername(param);
        } else if (type == 2)
        {
            tbUser.setPhone(param);
        } else if (type == 3)
        {
            tbUser.setEmail(param);
        }
        Long count = tbUserDubboService.selectByCount(tbUser);
        if (count.longValue() == 0)
        {
            return EgoResult.ok(true);
        }
        return EgoResult.error("用户不可用");
    }

    @Override
    public EgoResult register(TbUser tbUser)
    {
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        Date date = new Date();

        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        int index = tbUserDubboService.register(tbUser);
        if (index == 1)
        {
            return EgoResult.ok();
        }
        return EgoResult.error("注册失败，请重新注册");
    }
}
