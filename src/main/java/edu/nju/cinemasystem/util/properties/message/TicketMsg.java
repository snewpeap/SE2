package edu.nju.cinemasystem.util.properties.message;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ticket-msg")
public class TicketMsg extends ArrangementMsg {
    private String balanceNotEnough;
    private String refundStrategyExist;
    private String refundDisable;
    private String seatBeenLocked;
    private String orderInvalid;
    private String couponInvalid;
    private String hasOrder;

    public String getRefundStrategyExist() {
        return refundStrategyExist;
    }

    public void setRefundStrategyExist(String refundStrategyExist) {
        this.refundStrategyExist = refundStrategyExist;
    }

    public String getRefundDisable() {
        return refundDisable;
    }

    public void setRefundDisable(String refundDisable) {
        this.refundDisable = refundDisable;
    }

    public String getBalanceNotEnough() {
        return balanceNotEnough;
    }

    public void setBalanceNotEnough(String balanceNotEnough) {
        this.balanceNotEnough = balanceNotEnough;
    }

    public String getSeatBeenLocked() {
        return seatBeenLocked;
    }

    public void setSeatBeenLocked(String seatBeenLocked) {
        this.seatBeenLocked = seatBeenLocked;
    }

    public String getOrderInvalid() {
        return orderInvalid;
    }

    public void setOrderInvalid(String orderInvalid) {
        this.orderInvalid = orderInvalid;
    }

    public String getCouponInvalid() {
        return couponInvalid;
    }

    public void setCouponInvalid(String couponInvalid) {
        this.couponInvalid = couponInvalid;
    }

    public String getHasOrder() {
        return hasOrder;
    }

    public void setHasOrder(String hasOrder) {
        this.hasOrder = hasOrder;
    }
}
