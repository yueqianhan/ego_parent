package com.ego.commons.pojo;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/20 0020-下午 19:24
 */
public class EasyUIDataGrid
{
    private Long total;
    private List<?> rows;

    @Override
    public String toString()
    {
        return "EasyUIDataGrid{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public Long getTotal()
    {
        return total;
    }

    public void setTotal(Long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }
}
