package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.PassportService;
import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author hanyueqian
 * @date 2019/10/9 0009-下午 15:23
 */
@Controller
public class PassportController
{

    @Autowired
    private PassportService passportService;

    @RequestMapping("/user/showLogin")
    public String showLogin(@RequestHeader(name = "Referer",required = false,defaultValue = "")String ref, Model model)
    {
        if (ref.endsWith("/user/showRegister"))
        {
            ref="";
        }
        model.addAttribute("redirect",ref);
        return "login";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public EgoResult login(TbUser tbUser)
    {
        return passportService.login(tbUser);
    }

    @GetMapping("/user/token/{token}")
    @ResponseBody
    @CrossOrigin
    public EgoResult getUser(@PathVariable String token)
    {
        return passportService.getUser(token);
    }

    @PostMapping("/user/logout/{token}")
    @ResponseBody
    @CrossOrigin
    public EgoResult logout(@PathVariable String token)
    {
        return passportService.logout(token);
    }

    @RequestMapping("/user/showRegister")
    public String showRegister()
    {
        return "register";
    }

    @GetMapping("/user/check/{param}/{type}")
    @ResponseBody
    public EgoResult check(@PathVariable String param,@PathVariable int type)
    {
        return passportService.check(param,type);
    }


    @PostMapping("/user/register")
    @ResponseBody
    public EgoResult register(TbUser tbUser)
    {
        return passportService.register(tbUser);
    }
}
