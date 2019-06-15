package edu.nju.cinemasystem.data.po;

import java.util.Date;

public class Coupon {
    private Integer id;

    private Date endTime;

    private Integer promotionId;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static Coupon assembleCouponPO(Date endTime, int promotionId, int userId){
        Coupon coupon = new Coupon();
        coupon.endTime = endTime;
        coupon.promotionId = promotionId;
        coupon.userId = userId;
        return coupon;
    }
}