package edu.nju.cinemasystem.web.controller.vip;

import edu.nju.cinemasystem.blservices.vip.VIPCardService;
import edu.nju.cinemasystem.blservices.vip.VIPManagement;
import edu.nju.cinemasystem.data.vo.RechargeReductionVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.PresentCouponForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class VipController {
    private final VIPCardService vipCardService;
    private final VIPManagement vipManagement;

    @Autowired
    public VipController(VIPCardService vipCardService, VIPManagement vipManagement) {
        this.vipCardService = vipCardService;
        this.vipManagement = vipManagement;
    }

    @GetMapping("/user/vip/card/get")
    public Response getVIPCard(@RequestParam int userId,HttpSession session) {
        int realUserID = (int) session.getAttribute("id");
        if(userId!=realUserID){
            return Response.fail("雨女无瓜");
        }
        return vipCardService.getVIPCard(userId);
    }

    @GetMapping("/user/vip/card/add")
    public Response buyable(HttpSession session){
        int userId = (int) session.getAttribute("id");
        return vipCardService.buyable(userId);
    }

    @PostMapping("/user/vip/card/add/{orderID}")
    public Response addVIPCard(@PathVariable String orderID,HttpSession session) {
        int userId = (int) session.getAttribute("id");
        return vipCardService.addVIPCard(userId, orderID);
    }

    /**
     * 获得充值优惠策略，满减这些的
     *
     * @return 优惠策略VO列表
     */
    @GetMapping("/user/vip/rechargeReduction")
    public Response getRechargeReduction() {
        return vipCardService.getRechargeReduction();
    }

    @GetMapping("/user/vip/history")
    public Response getRechargeHistory(@RequestParam int userId, HttpSession session) {
        int realUserID = (int) session.getAttribute("id");
        if(userId!=realUserID){
            return Response.fail("雨女无瓜");
        }
        return vipCardService.getRechargeHistory(userId);
    }

    /**
     * 向后端请求支付金额，方法是GET，后端会进行检查，如果成功会返回一个vip.DelayedTask对象，代表目前的订单
     * 如果失败且状态码为 777，则意味着有未支付的订单，这时Response里会带有一个vip.DelayedTask对象，代表未支付订单
     *
     * @param session session
     * @param amount  原始的充值金额
     * @return 成功或失败的结果
     */
    @GetMapping("/user/vip/deposit")
    public Response depositable(HttpSession session, @RequestParam float amount) {
        int userId = (int) session.getAttribute("id");
        return vipCardService.depositable(userId, amount);
    }

    /**
     * 真正的支付，方法是POST
     *
     * @param session session
     * @param orderID 订单id，在上一个方法中已经给了
     * @return 成功或失败的结果
     */
    @PostMapping("/user/vip/deposit/{orderID}")
    public Response payDeposit(HttpSession session, @PathVariable String orderID) {
        int userId = (int) session.getAttribute("id");
        return vipCardService.deposit(userId, orderID);
    }

    @GetMapping("/admin/vip/get")
    public Response getVIPs() {
        return vipManagement.getVIPs();
    }

    @Deprecated
    @GetMapping("/admin/vip/get/{money}")
    public Response getVIPs(@PathVariable double money) {
        return vipManagement.getVIPs(money);
    }

    @PostMapping("/admin/vip/presentCoupon")
    public Response presentCoupon(@RequestBody PresentCouponForm presentCouponForm) {
        return vipManagement.presentCoupon(presentCouponForm.getVips(), presentCouponForm.getPromotionIDs());
    }

    @PostMapping(value = {"/admin/vip/reduction", "/admin/vip/reduction/add"})
    public Response addReduction(@RequestBody RechargeReductionVO reductionVO) {
        return vipManagement.addReduction(reductionVO);
    }

    @PostMapping("/admin/vip/reduction/modify")
    public Response modifyReduction(@RequestBody RechargeReductionVO reductionVO) {
        return vipManagement.modifyReduction(reductionVO);
    }

    @PostMapping("/admin/vip/reduction/delete")
    public Response removeReduction(@RequestParam int targetAmount) {
        return vipManagement.removeReduction(targetAmount);
    }

    @GetMapping("/admin/vip/reduction/get")
    public Response getReduction() {
        return vipCardService.getRechargeReduction();
    }
}
