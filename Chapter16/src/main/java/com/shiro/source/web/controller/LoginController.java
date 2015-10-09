package com.shiro.source.web.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UnknownFormatConversionException;

/**
 * Created by liwang on 15-10-9.
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        String exceptionName = (String) request.getAttribute("shiroLoginFailure");
        String error = null;
        if (UnknownFormatConversionException.class.getName().equals(exceptionName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionName)) {
            error = "用户名/密码错误";
        } else if (exceptionName != null) {
            error = "其他错误" + exceptionName;
        }
        model.addAttribute("error", error);
        return "login";
    }

}
