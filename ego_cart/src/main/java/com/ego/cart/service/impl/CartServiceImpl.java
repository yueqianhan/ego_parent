package com.ego.cart.service.impl;

import com.ego.cart.pojo.CartPojo;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.EgoStatic;
import com.ego.commons.utils.JsonUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/10/10 0010-下午 15:27
 */
@Service
public class CartServiceImpl implements CartService
{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${custom.redis.cart}")
    private String cartKey;

    @Reference
    private TbItemDubboService tbItemDubboService;
    @Override
    public boolean addCart(Long id, int num)
    {
        //判断用户是否已经登录
        String token = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN");
        //cookie中有token
        if (token!=null&&!token.equals(""))
        {
            if (redisTemplate.hasKey(token))
            {
                //用户已经登录了
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
                TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(token);
                String key =cartKey+":"+tbUser.getId();
                //redis中的value存放所有购物车数据
                List<CartPojo> list=null;
                if (redisTemplate.hasKey(key))
                {
                    redisTemplate.setValueSerializer(new StringRedisSerializer());
                    String listCart = redisTemplate.opsForValue().get(key).toString();
                    list= JsonUtils.jsonToList(listCart, CartPojo.class);
                }else
                {
                    //redis中没有用户购物车数据
                    list =new ArrayList<>();
                }
                //判断要买的商品是否存在于购物车中
                CartPojo cpExits =null;
                for (CartPojo cp:list)
                {
                    if (cp.getId().equals(id))
                    {
                        cpExits =cp;
                        break;
                    }
                }
                if (cpExits!=null)
                {
                    //如果存在只对购物车中数量进行改变
                    cpExits.setNum(cpExits.getNum()+num);
                }else
                {
                    //如果不存在，新增到购物车中
                    TbItem tbItem =tbItemDubboService.selectById(id);
                    CartPojo cartPojo =new CartPojo();
                    BeanUtils.copyProperties(tbItem,cartPojo);
                    String image = tbItem.getImage();
                    cartPojo.setImages(image!=null&&!image.equals("")?image.split(","):new String[1]);
                    cartPojo.setNum(num);
                    list.add(cartPojo);
                }
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
                redisTemplate.opsForValue().set(key,list);
                return true;
            }
        }

        //执行到此处说明没有登录,把商品放入临时购物车中
        String tempCartKey="tempCart";
        String cartListJson = CookieUtils.getCookieValue(ServletUtil.getRequest(), tempCartKey, true);
        List<CartPojo> tempCart =null;
        //已经有临时购物车
        if (Strings.isNotBlank(cartListJson))
        {
            tempCart =JsonUtils.jsonToList(cartListJson,CartPojo.class);
        }else
        {
            tempCart=new ArrayList<>();
        }

        //判断要买的商品是否存在于购物车中
        CartPojo cpExits =null;
        for (CartPojo cp:tempCart)
        {
            if (cp.getId().equals(id))
            {
                cpExits =cp;
                break;
            }
        }
        if (cpExits!=null)
        {
            //如果存在只对购物车中数量进行改变
            cpExits.setNum(cpExits.getNum()+num);
        }else
        {
            //如果不存在，新增到购物车中
            TbItem tbItem =tbItemDubboService.selectById(id);
            CartPojo cartPojo =new CartPojo();
            BeanUtils.copyProperties(tbItem,cartPojo);
            String image = tbItem.getImage();
            cartPojo.setImages(image!=null&&!image.equals("")?image.split(","):new String[1]);
            cartPojo.setNum(num);
            tempCart.add(cartPojo);
        }
        //把数据放到临时购物车中
        CookieUtils.setCookie(ServletUtil.getRequest(),ServletUtil.getResponse(),tempCartKey,JsonUtils.objectToJson(tempCart), EgoStatic.TWO_WEEK,true);
        return false;
    }

    @Override
    public List<CartPojo> showCart()
    {
        //判断用户是否已经登录
        String token = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN");
        //cookie中有token
        if (token!=null&&!token.equals(""))
        {
            if (redisTemplate.hasKey(token))
            {
                //用户已经登录了
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
                TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(token);
                String key = cartKey + ":" + tbUser.getId();
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                String listCart = redisTemplate.opsForValue().get(key).toString();
                return JsonUtils.jsonToList(listCart,CartPojo.class);
            }
        }
        String json = CookieUtils.getCookieValue(ServletUtil.getRequest(), "tempCart", true);
        if (Strings.isNotBlank(json))
        {
            return JsonUtils.jsonToList(json,CartPojo.class);
        }
        return new ArrayList<CartPojo>();
    }

    @Override
    public boolean mergeCart(String tempCart, String token123)
    {
        String key =null;

        //临时购物车
        List<CartPojo> listTemp =null;
        String json = CookieUtils.getCookieValue(ServletUtil.getRequest(), "tempCart", true);
        if (Strings.isNotBlank(json))
        {
            listTemp= JsonUtils.jsonToList(json, CartPojo.class);
        }else
        {
            listTemp =new ArrayList<>();
        }

        //用户购物车
        List<CartPojo> listUser =new ArrayList<>();
        //cookie中有token
        String token = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN");
        if (token!=null&&!token.equals(""))
        {
            if (redisTemplate.hasKey(token))
            {
                //用户已经登录
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
                TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(token);
                key =cartKey+":"+tbUser.getId();
                if (redisTemplate.hasKey(key))
                {
                    redisTemplate.setValueSerializer(new StringRedisSerializer());
                    String listCart = redisTemplate.opsForValue().get(key).toString();
                    listUser=JsonUtils.jsonToList(listCart,CartPojo.class);
                }
            }
        }

        //购物车中的商品总量
        List<CartPojo> totalCart =new ArrayList<>();
        //临时购物车
        for (CartPojo cp:listTemp)
        {
            CartPojo cp3 =null;
            for (CartPojo cp2:totalCart)
            {
                if (cp2.getId().equals(cp.getId()))
                {
                    cp3=cp2;
                    break;
                }
            }
            if (cp3!=null)
            {
                cp3.setNum(cp3.getNum()+cp.getNum());
            }else
            {
                totalCart.add(cp);
            }
        }

        for (CartPojo cp:listUser)
        {
            CartPojo cp3 =null;
            for (CartPojo cp2:totalCart)
            {
                if (cp2.getId().equals(cp.getId()))
                {
                    cp3=cp2;
                    break;
                }
            }
            if (cp3!=null)
            {
                cp3.setNum(cp3.getNum()+cp.getNum());
            }else
            {
                totalCart.add(cp);
            }
        }

//        listTemp.addAll(listUser);


        //放入到用户购物车中
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.opsForValue().set(key,listTemp);
        //清空临时购物车
        CookieUtils.deleteCookie(ServletUtil.getRequest(),ServletUtil.getResponse(),"tempCart");
        return true;
    }

    @Override
    public EgoResult deleteById(Long id)
    {
        String token = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN");
        if (token!=null&&!token.equals(""))
        {
            if (redisTemplate.hasKey(token))
            {
                //用户已经登录了
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
                TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(token);
                String key =cartKey+":"+tbUser.getId();
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                String json = redisTemplate.opsForValue().get(key).toString();
                List<CartPojo> list = JsonUtils.jsonToList(json, CartPojo.class);
                for (CartPojo cp:list)
                {
                    if (cp.getId().equals(id))
                    {
                        list.remove(cp);
                        break;
                    }
                }
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
                redisTemplate.opsForValue().set(key,list);
                return EgoResult.ok();
            }
        }
        return EgoResult.error("删除失败");
    }

    @Override
    public List<CartPojo> showOrderCart(List<Long> ids)
    {
        String token = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN");
        if (token!=null&&!token.equals(""))
        {
            if (redisTemplate.hasKey(token))
            {
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
                TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(token);
                String key =cartKey+":"+tbUser.getId();
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                String json = redisTemplate.opsForValue().get(key).toString();
                List<CartPojo> list = JsonUtils.jsonToList(json, CartPojo.class);
                List<CartPojo> listOrder =new ArrayList<>();

                for (CartPojo cp:list)
                {
                    if (ids.contains(cp.getId()))
                    {
                        //对cp进行判断，cp要购买的数量和库存进行比较
                        TbItem tbItem = tbItemDubboService.selectById(cp.getId());
                        if (tbItem.getNum()>=cp.getNum())
                        {
                            cp.setEnough(true);
                        }else
                        {
                            cp.setEnough(false);
                        }
                        listOrder.add(cp);
                    }
                }
                return listOrder;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public EgoResult updateCartNum(Long id, int num)
    {
        String token = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN");
        if (token!=null&&!token.equals(""))
        {
            if (redisTemplate.hasKey(token))
            {
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
                TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(token);
                String key = cartKey + ":" + tbUser.getId();
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                String json = redisTemplate.opsForValue().get(key).toString();
                List<CartPojo> list = JsonUtils.jsonToList(json, CartPojo.class);

                for (CartPojo cp:list)
                {
                    if (cp.getId().equals(id))
                    {
                        cp.setNum(num);
                    }
                }
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
                redisTemplate.opsForValue().set(key,list);
                return EgoResult.ok();
            }
        }
        return EgoResult.error("修改失败");
    }
}
