package com.ego.cart.service;

import com.ego.cart.pojo.CartPojo;
import com.ego.commons.pojo.EgoResult;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/10/10 0010-下午 15:26
 */
public interface CartService
{
    /**
     * 添加购物车
     * @return
     */
    boolean addCart(Long id,int num);

    /**
     * 显示购物车
     * @return
     */
    List<CartPojo> showCart();


    /**
     * 合并购物车
     * @param tempCart
     * @param token123
     * @return
     */
    boolean mergeCart(String tempCart,String token);

    /**
     * 根据id删除购物车中数据
     * @param id
     * @return
     */
    EgoResult deleteById(Long id);

    /**
     * 显示结算页面
     * @param id
     * @return
     */
    List<CartPojo> showOrderCart(List<Long> ids);

    /**
     * 修改购物车中商品数量
     * @param id
     * @param num
     * @return
     */
    EgoResult updateCartNum(Long id,int num);
}
