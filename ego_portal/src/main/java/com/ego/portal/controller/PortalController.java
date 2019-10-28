package com.ego.portal.controller;

import com.ego.portal.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hanyueqian
 * @date 2019/9/27 0027-下午 15:02
 */
@Controller
public class PortalController
{
    @Autowired
    private PortalService portalService;
    @RequestMapping("/")
    public String welcome(Model model)
    {
        model.addAttribute("ad1",portalService.showAd());
        return "index";
    }
}
