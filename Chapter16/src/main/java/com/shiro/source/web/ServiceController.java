package com.shiro.source.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nikolas on 2015/10/8.
 */
@RestController
public class ServiceController {

    @RequestMapping("/hello")
    public String hello1(@RequestParam("param1")String[] param1, @RequestParam("param2")String param2) {
        return "hello" + param1[0] + param1[1] + param2;
    }
}