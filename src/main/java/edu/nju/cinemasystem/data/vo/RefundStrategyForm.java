package edu.nju.cinemasystem.data.vo;

public class RefundStrategyForm {
    private Integer day;

    private Boolean refundable;

    private Float percentage;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public Boolean getRefundable() {
        return refundable;
    }


}