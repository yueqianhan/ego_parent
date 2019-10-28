package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/10/10 0010-下午 14:45
 */
@Controller
public class CartController
{
    @Autowired
    private CartService cartService;


    @RequestMapping("/cart/add/{id}.html")
    public String  addCart(@PathVariable Long id, int num)
    {
        cartService.addCart(id,num);
        return "cartSuccess";
    }

    @RequestMapping("/cart/cart.html")
    public String showCart(Model model)
    {
        model.addAttribute("cartList",cartService.showCart());
        return "cart";
    }

    @GetMapping("/cart/merge")
    @CrossOrigin
    public String mergeCart(String tempCart,String token,String url)
    {
        cartService.mergeCart(tempCart,token);
        return "redirect:"+url;
    }

    @RequestMapping("/cart/delete/{id}.action")
    @ResponseBody
    public EgoResult deleteById(@PathVariable Long id)
    {
        return cartService.deleteById(id);
    }

    @RequestMapping("/cart/order-cart.html")
    public String showOrder(@RequestParam("id") List<Long> id,Model model)
    {
        model.addAttribute("cartList",cartService.showOrderCart(id));
        return "order-cart";
    }

    @PostMapping("/cart/update/num/{id}/{num}.action")
    @ResponseBody
    public EgoResult update(@PathVariable Long id,@PathVariable  int num)
    {
        return cartService.updateCartNum(id,num);
    }
}
