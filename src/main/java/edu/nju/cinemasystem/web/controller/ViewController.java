package edu.nju.cinemasystem.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/index")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/register")
    public String getSignUp() {
        return "register";
    }

    @RequestMapping("/admin/manage/activity")
    public String getAdminActivityManage(){
        return "admin/activityManage";
    }

    @RequestMapping("/admin/manage/hall")
    public String getAdminManageHall(){
        return "admin/hallManage";
    }

    @RequestMapping("/admin/activity")
    public String getAdminActivity(){
        return "admin/hallManage";
    }
}
