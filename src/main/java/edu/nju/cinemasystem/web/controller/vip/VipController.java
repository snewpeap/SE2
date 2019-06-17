package edu.nju.cinemasystem.web.controller.vip;

import edu.nju.cinemasystem.blservices.vip.VIPCard;
import edu.nju.cinemasystem.blservices.vip.VIPManagement;
import edu.nju.cinemasystem.data.vo.RechargeReductionVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.PresentCouponForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VipController {
    @Autowired
    private VIPCard vipCard;
    @Autowired
    private VIPManagement vipManagement;

    @GetMapping("/user/vip/card/get")
    public Response getVIPCard(@RequestParam int userId){
        return vipCard.getVIPCard(userId);
    }

    @GetMapping("/user/vip/card/add")
    public Response addVIPCard(@RequestParam int userId){
        return vipCard.addVIPCard(userId);
    }

    /**
     * 获得充值优惠策略，满减这些的
     * @return 优惠策略VO列表
     */
    @GetMapping("/user/vip/rechargeReduction")
    public Response getRechargeReduction(){
        return vipCard.getRechargeReduction();
    }

    @GetMapping("/user/vip/history")
    public Response getRechargeHistory(@RequestParam int userId){
        return vipCard.getRechargeHistory(userId);
    }

    @PostMapping("/user/vip/deposit")
    public Response depositVIPCard(@RequestParam int userId,@RequestBody float amount){
        return vipCard.deposit(userId,amount);
    }

    @GetMapping("/admin/vip/get")
    public Response getVIPs(){
        return vipManagement.getVIPs();
    }

    @GetMapping("/admin/vip/get/{money}")
    public Response getVIPs(@PathVariable double money){
        return vipManagement.getVIPs(money);
    }

    @PostMapping("/admin/vip/presentCoupon")
    public Response presentCoupon(@RequestBody PresentCouponForm presentCouponForm){
        return vipManagement.presentCoupon(presentCouponForm.getVips(),presentCouponForm.getPromotionIDs());
    }

    @PostMapping(value = {"/admin/vip/reduction","/admin/vip/reduction/add"})
    public Response addReduction(@RequestBody RechargeReductionVO reductionVO){
        return vipManagement.addReduction(reductionVO);
    }

    @PostMapping("/admin/vip/reduction/modify")
    public Response modifyReduction(@RequestBody RechargeReductionVO reductionVO){
        return vipManagement.modifyReduction(reductionVO);
    }

    @PostMapping("/admin/vip/reduction/delete/{id}")
    public Response removeReduction(@PathVariable int id){
        return vipManagement.removeReduction(id);
    }

    @GetMapping("/admin/vip/reduction/get")
    public Response getReduction(){
        return vipCard.getRechargeReduction();
    }
}
