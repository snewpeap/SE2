package edu.nju.cinemasystem.blservices.impl.sale.promotion;

import edu.nju.cinemasystem.blservices.movie.SaleInfo;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.PromotionForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionImpl implements
        edu.nju.cinemasystem.blservices.sale.promotion.Promotion,
        edu.nju.cinemasystem.blservices.sale.promotion.Coupon, SaleInfo {

    @Override
    public Response getAllPromotions() {
        return null;
    }

    @Override
    public double getHeatOf(int movieID) {
        //TODO
        return 0;
    }

    @Override
    public List<Integer> getJoinedPromotionOf(int movieID) {
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