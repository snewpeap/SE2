package edu.nju.cinemasystem.data.vo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class RefundStrategyForm {
    @NotNull
    @PositiveOrZero
    private Integer day;

    private Boolean refundable;

    @NotNull
    @PositiveOrZero
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