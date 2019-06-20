package edu.nju.cinemasystem.data.vo.statisticsVO;

import edu.nju.cinemasystem.data.po.RefundStrategy;

/**
 * 退票策略信息 包括：
 * day 离电影放映还有？天 0为不可退票，1为可以退票
 * refundable 是否可退
 * percentage 返还金额占百分比
 */
public class RefundStrategyVO {
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

    public void setRefundable(Byte refundable) {
        this.refundable = refundable;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public RefundStrategyVO(RefundStrategy refundStrategy) {
        this.day = refundStrategy.getDay();
        this.percentage = refundStrategy.getPercentage();
        this.refundable = refundStrategy.getRefundable();
    }
}
