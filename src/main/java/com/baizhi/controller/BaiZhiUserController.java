package com.baizhi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.baizhi.entity.BaiZhiResource;
import com.baizhi.service.BaiZhiPageService;
import com.baizhi.service.BaiZhiResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baizhi.entity.BaiZhiUser;
import com.baizhi.service.BaiZhiUserService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class BaiZhiUserController {
    @Autowired
    private BaiZhiUserService service;
    @Autowired
    private BaiZhiPageService baiZhiPageService;
    @Autowired
    private BaiZhiResourceService baiZhiResourceService;

    public BaiZhiPageService getBaiZhiPageService() {
        return baiZhiPageService;
    }

    public void setBaiZhiPageService(BaiZhiPageService baiZhiPageService) {
        this.baiZhiPageService = baiZhiPageService;
    }

    public BaiZhiResourceService getBaiZhiResourceService() {
        return baiZhiResourceService;
    }

    public void setBaiZhiResourceService(BaiZhiResourceService baiZhiResourceService) {
        this.baiZhiResourceService = baiZhiResourceService;
    }

    public BaiZhiUserService getService() {
        return service;
    }

    public void setService(BaiZhiUserService service) {
        this.service = service;
    }

    @RequestMapping("tologin")
    public String toupload(HttpServletRequest request) {
        String status = (String) request.getSession().getAttribute("languageStatus");
        if (status == null) {
            status = "0";
        }
        String content = baiZhiPageService.queryContentByPageNameAndStatus("login.jsp", status);
        String[] contents = content.split("_");
        request.setAttribute("contents", contents);
        return "login-form";
    }

    @RequestMapping("toreg")
    public String toreg(HttpServletRequest request) {
        String status = (String) request.getSession().getAttribute("languageStatus");
        if (status == null) {
            status = "0";
        }
        String content = baiZhiPageService.queryContentByPageNameAndStatus("reg.jsp", status);
        String[] contents = content.split("_");
        request.setAttribute("contents", contents);
        return "reg-form";
    }

    @RequestMapping("login")
    public @ResponseBody
    String login(HttpSession session, String username, String password) {

        try {
            BaiZhiUser user = service.login(username, password);
            session.setAttribute("user", user);
            return "loginok";
        } catch (Exception e) {
            e.printStackTrace();
            return "loginno";
        }

    }

    @RequestMapping("reg")
    public String reg(HttpSession session, BaiZhiUser user) {
        BaiZhiUser reg = service.reg(user);
        System.out.println("用户注册成功！");
        session.setAttribute("user", reg);
        return "redirect:/user/tomain";
    }

    @RequestMapping("tomain")
    public String toMain(HttpServletRequest request, HttpSession session) {
        String status = (String) request.getSession().getAttribute("languageStatus");
        if (status == null) status = "0";
        String content = baiZhiPageService.queryContentByPageNameAndStatus("main.jsp", status);
        BaiZhiResource resource = baiZhiResourceService.getResource();
        session.setAttribute("resource", resource);
        String[] contents = content.split("_");
        request.setAttribute("contents", contents);
        return "/main";
    }

    @ResponseBody
    @RequestMapping("createtoken")
    public void createtoken(HttpSession session) {
        session.setAttribute("token", new Date());

    }

    @ResponseBody
    @RequestMapping("checktoken")
    public Boolean checktoken(HttpSession session) {
        Object token = session.getAttribute("token");
        Date oldtoken = (Date) token;
        long l = (new Date().getTime() - oldtoken.getTime()) / 1000;
        if (l >= 5) {
            return false;
        } else {
            return true;
        }
    }


    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/tomain";
    }
}
