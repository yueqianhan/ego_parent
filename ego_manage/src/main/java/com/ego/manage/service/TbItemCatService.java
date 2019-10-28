package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 15:02
 */
public interface TbItemCatService
{
    List<EasyUITreeNode> selectByPid(Long pid);
}
