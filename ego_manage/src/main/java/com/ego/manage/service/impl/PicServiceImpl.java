package com.ego.manage.service.impl;

import com.ego.commons.utils.FastDFSClient;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hanyueqian
 * @date 2019/9/23 0023-下午 16:19
 */
@Service
public class PicServiceImpl implements PicService
{
    @Value("${custom.fastdfs.nginx}")
    private String nginxUrl;
    @Override
    public Map<String, Object> upload(MultipartFile file)
    {
        Map<String,Object> map =new HashMap<>();
        try
        {
            String oldName = file.getOriginalFilename();
            String fileName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf("."));
            String[] result = FastDFSClient.uploadFile(file.getInputStream(), fileName);

            map.put("error",0);
            map.put("url",nginxUrl+result[0]+"/"+result[1]);
            return map;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        map.put("error",1);
        map.put("message","图片上传失败");
        return map;
    }
}
