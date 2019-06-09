package edu.nju.cinemasystem.data.po;

import java.util.Date;

public class Order {
    private Long id;

    private Float realAmount;

    private Float originalAmount;

    private Date date;

    private Byte useVipcard; //0，1，2，3分别代表银行卡支付，会员卡支付，未支付，已失效

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

    public Byte getUseVipcard() {
        return useVipcard;
    }

    public void setUseVipcard(Byte useVipcard) {
        this.useVipcard = useVipcard;
    }

    public Order(Long id, Float realAmount, Float originalAmount, Date date, Byte useVipcard) {
        this.id = id;
        this.realAmount = realAmount;
        this.originalAmount = originalAmount;
        this.date = date;
        this.useVipcard = useVipcard;
    }
}