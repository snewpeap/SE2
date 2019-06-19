package edu.nju.cinemasystem.data.po;

import java.util.Date;

public class Order {
    private Long id;

    private Float realAmount;

    private Float originalAmount;

    private Date date;

    private Byte status; //0，1，2，3分别代表银行卡支付，会员卡支付，未支付，已失效

    private Integer userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Float realAmount) {
        this.realAmount = realAmount;
    }

    public Float getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Float originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static Order assembleOrderPO(Long id, Float realAmount, Float originalAmount, Date date, Byte useVipcard, Integer userId) {
        Order order = new Order();
        order.id = id;
        order.realAmount = realAmount;
        order.originalAmount = originalAmount;
        order.date = date;
        order.status = useVipcard;
        order.userId = userId;
        return order;
    }
}