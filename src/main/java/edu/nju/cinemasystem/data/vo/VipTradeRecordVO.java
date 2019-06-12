package edu.nju.cinemasystem.data.vo;

import java.util.Date;

public class VipTradeRecordVO {
    private Integer id;//交易号

    private Float originalAmount;//交易前余额

    private Float delta;//交易金额

    private Date date;

    private Integer userId;//相当于卡号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Float originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Float getDelta() {
        return delta;
    }

    public void setDelta(Float delta) {
        this.delta = delta;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
