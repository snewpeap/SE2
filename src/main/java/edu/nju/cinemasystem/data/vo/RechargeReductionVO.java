package edu.nju.cinemasystem.data.vo;

/**
 * 充值记录信息 包括：
 * targetAmount 充值金额
 * discountAmount 折扣金额
 */
public class RechargeReductionVO {
    private int targetAmount;
    private float discountAmount;

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }
}
