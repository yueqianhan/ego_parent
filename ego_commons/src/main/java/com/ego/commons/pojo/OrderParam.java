package com.ego.commons.pojo;

import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/10/12 0012-下午 15:00
 */
public class OrderParam implements Serializable
{

    private Integer paymentType;

    private String payment;

    private List<TbOrderItem> orderItems;

    private String token;
    private TbOrderShipping orderShipping;

    @Override
    public String toString()
    {
        return "OrderParam{" +
                "paymentType=" + paymentType +
                ", payment='" + payment + '\'' +
                ", orderItems=" + orderItems +
                ", token='" + token + '\'' +
                ", orderShipping=" + orderShipping +
                '}';
    }

    public Integer getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType)
    {
        this.paymentType = paymentType;
    }

    public String getPayment()
    {
        return payment;
    }

    public void setPayment(String payment)
    {
        this.payment = payment;
    }

    public List<TbOrderItem> getOrderItems()
    {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems)
    {
        this.orderItems = orderItems;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public TbOrderShipping getOrderShipping()
    {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping)
    {
        this.orderShipping = orderShipping;
    }
}
