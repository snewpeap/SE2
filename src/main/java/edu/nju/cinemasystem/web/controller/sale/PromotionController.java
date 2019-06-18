package edu.nju.cinemasystem.web.controller.sale;

import edu.nju.cinemasystem.blservices.sale.promotion.CouponService;
import edu.nju.cinemasystem.blservices.sale.promotion.PromotionService;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.PromotionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PromotionController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/user/coupon/get")
    public Response getAvailableCoupon(@RequestParam int userId){
        return couponService.getAvailableCouponsByUser(userId);
    }

    @PostMapping("/admin/promotion/add")
    public Response publishPromotion(@RequestBody PromotionForm promotionForm){
        return promotionService.publishPromotion(promotionForm);
    }

    @GetMapping("/admin/promotion/get")
    public Response getAllPromotions(){
        return promotionService.getAllPromotions();
    }
}
