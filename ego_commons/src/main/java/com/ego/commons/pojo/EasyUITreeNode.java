package com.ego.commons.pojo;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 15:03
 */

/**
 * 封装EasyUI Tree的实体类
 */
public class EasyUITreeNode
{
    private Long id;
    private String text;
    private String state;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
