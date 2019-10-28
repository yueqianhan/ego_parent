package com.ego.cart.pojo;

import com.ego.pojo.TbItem;

/**
 * @author hanyueqian
 * @date 2019/10/10 0010-下午 15:37
 */
public class CartPojo extends TbItem
{
    private String[] images;

    private boolean enough;

    public boolean isEnough()
    {
        return enough;
    }

    public void setEnough(boolean enough)
    {
        this.enough = enough;
    }

    public String[] getImages()
    {
        return images;
    }

    public void setImages(String[] images)
    {
        this.images = images;
    }
}
