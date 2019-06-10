package edu.nju.cinemasystem.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    @RequestMapping("/index")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/signUp")
    public String getRegister() {
        return "register";
    }

    @RequestMapping("/admin/activity")
    public String getAdminActivityManage(){
        return "admin/activityManage";
    }

    @RequestMapping("/manage/arrangement")
    public String getManageArrangement(){
        return "admin/arrangementManage";
    }

    @RequestMapping("/admin/hall")
    public String getAdminManageHall(){
        return "admin/hallManage";
    }

    @RequestMapping("/admin/handout/coupon")
    public String getAdminHandoutCoupon(){
        return "admin/handOutCoupon";
    }

    @RequestMapping("/manage/movie/detail")
    public String getManageMovieDetail(@RequestParam int movieId){
        return "admin/movieDetail";
    }

    @RequestMapping("/manage/movie")
    public String getManageMovie(){
        return "admin/movieManage";
    }

    @RequestMapping("/admin/refundStrategy")
    public String getAdminRefundStrategy(){
        return "admin/refundStrategy";
    }

    @RequestMapping("/root/staff")
    public String getRootStaff(){
        return "admin/staffManage";
    }

    @RequestMapping("/manage/statistics")
    public String getManageStatistics(){
        return "admin/statistics";
    }

    @RequestMapping("/admin/vipStrategy")
    public String getAdminVipStrategy(){
        return "admin/vipStrategyManage";
    }

    @RequestMapping("/user/buy")
    public String getUserBuy(){
        return "user/buy";
    }

    @RequestMapping("/user/cardPocket")
    public String getUserCardPocket(){
        return "user/cardPocket";
    }

    @RequestMapping("/user/consumeRecord")
    public String getUserConsumeRecord(){
        return "user/consumeRecord";
    }

    @RequestMapping("/user/home")
    public String getUserHome(){
        return "user/home";
    }

    @RequestMapping("/user/movie")
    public String getUserMovie(){
        return "user/movie";
    }

    @RequestMapping("/user/rechargeRecord")
    public String getUserRechargeRecord(){
        return "user/rechargeRecord";
    }

    @RequestMapping("user/ticket")
    public String getUserTicket(){
        return "user/ticket";
    }

    @RequestMapping("/user/movie/detail")
    public String getUserMovieDetail(){
        return "user/viewMovieDetail";
    }
}