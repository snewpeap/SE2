package edu.nju.cinemasystem.data.vo;

public class RefundStrategyForm {
    private Integer day;

    private Byte refundable;

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

    public void setRefundable(Byte refundable) {
        this.refundable = refundable;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }


}