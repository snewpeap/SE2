package edu.nju.cinemasystem.data.vo.form;

import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public class PresentCouponForm {
    @UniqueElements
    private List<Integer> vips;

    @UniqueElements
    private List<Integer> promotionIDs;

    public List<Integer> getVips() {
        return vips;
    }

    public void setVips(List<Integer> vips) {
        this.vips = vips;
    }

    public List<Integer> getPromotionIDs() {
        return promotionIDs;
    }

    public void setPromotionIDs(List<Integer> promotionIDs) {
        this.promotionIDs = promotionIDs;
    }
}
