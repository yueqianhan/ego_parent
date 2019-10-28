package com.ego.item.pojo;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/10/8 0008-下午 16:00
 */
public class ItemParam
{
    private String group;
    private List<ItemParamKeyValue> params;

    public String getGroup()
    {
        return group;
    }

    public void setGroup(String group)
    {
        this.group = group;
    }

    public List<ItemParamKeyValue> getParams()
    {
        return params;
    }

    public void setParams(List<ItemParamKeyValue> params)
    {
        this.params = params;
    }
}
