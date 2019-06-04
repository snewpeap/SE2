package edu.nju.cinemasystem.data.vo;

public class CouponVO{
    private int ID;
    private Float targetAmount;
    private Float discountAmount;

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
    
}