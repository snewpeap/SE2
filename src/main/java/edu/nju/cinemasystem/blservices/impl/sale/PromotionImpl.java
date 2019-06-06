package edu.nju.cinemasystem.blservices.impl.sale;

import edu.nju.cinemasystem.blservices.sale.promotion.Coupon;
import edu.nju.cinemasystem.blservices.sale.promotion.Promotion;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.PromotionForm;
import org.springframework.stereotype.Service;

@Service
public class PromotionImpl implements Promotion, Coupon {

    @Override
    public Response getAllPromotions() {
        return null;
    }

    @Override
    public Response publishPromotion(PromotionForm promotionForm) {
        return null;
    }

    @Override
    public Response getAvailableCouponsByUser(int userId) {
        return null;
    }

    
}