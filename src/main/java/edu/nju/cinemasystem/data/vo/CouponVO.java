package edu.nju.cinemasystem.data.vo;

import edu.nju.cinemasystem.data.po.Coupon;

import java.util.Date;

public class CouponVO {
    private int ID;
    private Float targetAmount;
    private Float discountAmount;
    private Date startDay;
    private Date endDay;
    private String promotionName;
    private String promotionDescription;


    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public Float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionDescription() {
        return promotionDescription;
    }

    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    public CouponVO(Coupon coupon) {
        this.ID = coupon.getId();
        this.endDay = coupon.getEndTime();
    }

}