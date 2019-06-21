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

    /**
     * 获取观众的所有可用优惠券
     * @param userId 用户id
     * @return 优惠券vo
     */
    @GetMapping("/user/coupon/get")
    public Response getAvailableCoupon(@RequestParam int userId){
        return couponService.getAvailableCouponsByUser(userId);
    }

    /**
     * 发布优惠活动
     * @param promotionForm 优惠活动表单
     * @return 添加优惠活动的结果
     */
    @PostMapping("/admin/promotion/add")
    public Response publishPromotion(@RequestBody PromotionForm promotionForm){
        return promotionService.publishPromotion(promotionForm);
    }

    /**
     * 获取所有优惠活动
     * @return 所有优惠活动vo
     */
    @GetMapping("/admin/promotion/get")
    public Response getAllPromotions(){
        return promotionService.getAllPromotions();
    }
}
