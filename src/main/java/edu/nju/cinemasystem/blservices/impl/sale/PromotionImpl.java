package edu.nju.cinemasystem.blservices.impl.sale;

import org.springframework.stereotype.Service;

import edu.nju.cinemasystem.blservices.sale.promotion.Coupon;
import edu.nju.cinemasystem.blservices.sale.promotion.Promotion;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.Form.PromotionForm;

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