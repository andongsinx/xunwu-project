package com.focus.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description： 普通用户控制器
 * @Author: shadow
 * @Date: create in 12:06 2019/1/5
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    /**
     * 用户登录页面
     * @return
     */
    @GetMapping("/login")
    public String loginPage(){
        return "user/login";
    }

    /**
     * 用户中心
     * @return
     */
    @GetMapping("/center")
    public String centerPage(){
        return "user/center";
    }


}
