package com.ego.manage.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 16:11
 */
public interface PicService
{
    /**
     * 文件上传
     * @param file
     * @return
     */
    Map<String,Object> upload(MultipartFile file);
}
