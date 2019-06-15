package edu.nju.cinemasystem.data.po;

public class RefundStrategy {
    private Integer day;

    private Byte refundable;//0为不可退票，1为可以退票

    private Float percentage;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Byte getRefundable() {
        return refundable;
    }

    public boolean canRefund() {
        return refundable == 1;
    }

    public void setRefundable(Byte refundable) {
        this.refundable = refundable;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public static RefundStrategy assembleRefundStrategyPO(Integer day, Byte refundable, Float percentage) {
        RefundStrategy refundStrategy = new RefundStrategy();
        refundStrategy.setDay(day);
        refundStrategy.setRefundable(refundable);
        refundStrategy.setPercentage(percentage);
        return refundStrategy;
    }
}