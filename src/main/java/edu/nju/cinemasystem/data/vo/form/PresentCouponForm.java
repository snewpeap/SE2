package edu.nju.cinemasystem.data.vo.form;

import java.util.List;

public class PresentCouponForm {
    private List<Integer> vips;
    private List<Integer> couponIds;

    public List<Integer> getVips() {
        return vips;
    }

    public void setVips(List<Integer> vips) {
        this.vips = vips;
    }

    public List<Integer> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<Integer> couponIds) {
        this.couponIds = couponIds;
    }
}
