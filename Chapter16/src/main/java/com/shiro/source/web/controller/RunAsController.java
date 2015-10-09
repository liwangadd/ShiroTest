package com.shiro.source.web.controller;

import com.shiro.source.entity.User;
import com.shiro.source.service.UserRunAsService;
import com.shiro.source.service.UserService;
import com.shiro.source.web.bind.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by liwang on 15-10-9.
 */
@Controller
@RequestMapping("/runas")
public class RunAsController {

    @Autowired
    private UserRunAsService userRunAsService;

    @Autowired
    private UserService userService;

    @RequestMapping
    public String runasList(@CurrentUser User user, Model model) {
        model.addAttribute("fromUserIds", userRunAsService.findFromUserIds(user.getId()));
        model.addAttribute("toUserIds", userRunAsService.findToUserIds(user.getId()));
        List<User> allUsers = userService.findAll();
        allUsers.remove(user);
        Subject subject = SecurityUtils.getSubject();
        model.addAttribute("isRunas", subject.isRunAs());
        if (subject.isRunAs()) {
            String previousUsername = (String) subject.getPreviousPrincipals().getPrimaryPrincipal();
            model.addAttribute("previousUsername", previousUsername);
        }
        return "runas";
    }

    @RequestMapping("/grant/{toUserId}")
    public String grant(@CurrentUser User loginUser, @PathVariable("toUserId") long toUserId, RedirectAttributes attribute) {
        if (!loginUser.getId().equals(toUserId)) {
            attribute.addFlashAttribute("msg", "自己不能切换到自己的身份");
            return "redirect:/runas";
        }
        userRunAsService.grantRunAs(loginUser.getId(), toUserId);
        attribute.addFlashAttribute("msg", "操作成功");
        return "redirect:/runas";
    }

    @RequestMapping("/revoke/{toUserId}")
    public String revoke(@CurrentUser User loginUser, @PathVariable("toUserId") long toUserId, RedirectAttributes attribute) {
        userRunAsService.revokeRunAs(loginUser.getId(), toUserId);
        attribute.addAttribute("msg", "操作成功");
        return "redirect:/runas";
    }

    @RequestMapping("/switchTo/{switchToUserId}")
    public String switchTo(@CurrentUser User loginUser, @PathVariable("switchToUserId") long toUserId, RedirectAttributes attribute) {
        Subject subject = SecurityUtils.getSubject();
        User switchToUser = userService.findOne(toUserId);
        if (loginUser.equals(switchToUser)) {
            attribute.addAttribute("msg", "自己不能切换到自己的身份");
            return "redirect:/runas";
        }
        if (switchToUser == null || !userRunAsService.exists(toUserId, loginUser.getId())) {
            attribute.addAttribute("msg", "对方没有授予您身份，不能切换");
            return "redirect:/runas";
        }
        subject.runAs(new SimplePrincipalCollection(switchToUser.getUsername(), ""));
        attribute.addFlashAttribute("msg", "操作成功");
        attribute.addFlashAttribute("needRefresh", "true");
        return "redirect:/runas";
    }

    @RequestMapping("/switchBack")
    public String switchBack(RedirectAttributes attribute) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRunAs()) {
            subject.releaseRunAs();
        }
        attribute.addFlashAttribute("msg", "操作成功");
        attribute.addFlashAttribute("needRefresh", "true");
        return "redirect:/runas";
    }

}
