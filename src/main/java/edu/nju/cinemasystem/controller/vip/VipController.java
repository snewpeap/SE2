package edu.nju.cinemasystem.controller.vip;

import edu.nju.cinemasystem.blservices.vip.VIPCard;
import edu.nju.cinemasystem.blservices.vip.VIPManagement;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vip")
public class VipController {
    @Autowired
    private VIPCard vipCard;
    @Autowired
    private VIPManagement vipManagement;

    @GetMapping("/card/get")
    public Response getVIPCard(@RequestParam int userId){
        return vipCard.getVIPCard(userId);
    }

    @GetMapping("/card/buy")
    public Response buyVIPCard(@RequestParam int userId){
        return vipCard.buyVIPCard(userId);
    }

    @GetMapping("/rechargeReduction")
    public Response getRechargeReduction(){
        return vipCard.getRechargeReduction();
    }

    @GetMapping("/rechargeHistory")
    public Response getRechargeHistory(@RequestParam int userId){
        return vipCard.getRechargeHistory(userId);
    }

    @GetMapping("/get/all")
    public Response getVIPs(){
        return vipManagement.getVIPs();
    }

    @GetMapping("/get/{money}")
    public Response getVIPs(@PathVariable double money){
        return vipManagement.getVIPs(money);
    }
}
