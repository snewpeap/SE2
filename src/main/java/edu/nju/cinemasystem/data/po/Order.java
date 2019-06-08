package edu.nju.cinemasystem.data.po;

import java.util.Date;

public class Order {
    private Long id;

    private Float realAmount;

    private Float originalAmount;

    private Date date;

    private Byte useVipcard;

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
}