package com.shiro.source.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liwang on 15-10-6.
 */
@Controller
public class AnnotationController {

    @RequestMapping("/hello1")
    public String hello1(){
        SecurityUtils.getSubject().checkRole("admin");
        return "success";
    }

    @RequiresRoles("admin")
    @RequestMapping("/hello2")
    public String hello2(){
        return "success";
    }

}
