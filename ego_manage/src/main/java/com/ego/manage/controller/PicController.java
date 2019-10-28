package com.ego.manage.controller;

import com.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 16:27
 */
@RestController
public class PicController
{
    @Autowired
    private PicService picService;

    //@RequestMapping("/pic/upload")
    @PostMapping("/pic/upload")
    //    @RequestMapping(name = "/pic/upload",method = RequestMethod.POST)
    public Map<String,Object> upload(MultipartFile uploadFile)
    {
        return picService.upload(uploadFile);
    }
}
