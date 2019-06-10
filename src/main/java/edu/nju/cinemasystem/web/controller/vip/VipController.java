package edu.nju.cinemasystem.web.controller.vip;

import edu.nju.cinemasystem.blservices.vip.VIPCard;
import edu.nju.cinemasystem.blservices.vip.VIPManagement;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.PresentCouponForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class VipController {
    @Autowired
    private VIPCard vipCard;
    @Autowired
    private VIPManagement vipManagement;

    @GetMapping("/user/vip/card/get")
    public Response getVIPCard(HttpSession session){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return vipCard.getVIPCard(userId);
    }

    @GetMapping("/user/vip/card/add")
    public Response addVIPCard(HttpSession session){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return vipCard.addVIPCard(userId);
    }

    @GetMapping("/user/vip/rechargeReduction")
    public Response getRechargeReduction(){
        return vipCard.getRechargeReduction();
    }

    @GetMapping("/user/vip/rechargeHistory")
    public Response getRechargeHistory(HttpSession session){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return vipCard.getRechargeHistory(userId);
    }

    @PostMapping("/user/vip/card/deposit")
    public Response depositVIPCard(HttpSession session,@RequestBody float amount){
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        return vipCard.deposit(userId,amount);
    }

//    @PostMapping("/user/vip/pay")

    @GetMapping("/admin/vip/get/all")
    public Response getVIPs(){
        return vipManagement.getVIPs();
    }

    @GetMapping("/admin/vip/get/{money}")
    public Response getVIPs(@PathVariable double money){
        return vipManagement.getVIPs(money);
    }

    @PostMapping("/admin/vip/presentCoupon")
    public Response presentCoupon(@RequestBody PresentCouponForm presentCouponForm){
        return vipManagement.presentCoupon(presentCouponForm.getVips(),presentCouponForm.getCouponIds());
    }
}
