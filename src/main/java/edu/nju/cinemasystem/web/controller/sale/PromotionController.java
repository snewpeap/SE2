package edu.nju.cinemasystem.web.controller.sale;

import edu.nju.cinemasystem.blservices.sale.promotion.Coupon;
import edu.nju.cinemasystem.blservices.sale.promotion.Promotion;
import edu.nju.cinemasystem.data.vo.Form.PromotionForm;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PromotionController {
    @Autowired
    private Coupon coupon;
    @Autowired
    private Promotion promotion;

    @GetMapping("/coupon/get")
    public Response getAvailableCoupon(@RequestParam int userId){
        return coupon.getAvailableCouponsByUser(userId);
    }

    @PostMapping("/promotion/add")
    public Response publishPromotion(@RequestBody PromotionForm promotionForm){
        return promotion.publishPromotion(promotionForm);
    }

    @GetMapping("/promotion/get")
    public Response getAllPromotions(){
        return promotion.getAllPromotions();
    }
}
