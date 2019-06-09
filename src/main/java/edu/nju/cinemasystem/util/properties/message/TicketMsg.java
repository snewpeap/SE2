package edu.nju.cinemasystem.util.properties.message;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ticket-msg")
public class TicketMsg extends ArrangementMsg {
    private String balanceNotEnough;
    private String refundStrategyExist;
    private String refundDisable;

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
}
