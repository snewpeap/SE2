package edu.nju.cinemasystem.data.po;

import java.util.Date;

public class Promotion {
    private Integer id;

    private String name;

    private Date startTime;

    private Date endTime;

    private Byte specifyMovies;

    private Float targetAmount;

    private Float couponAmount;

    private Integer couponExpiration;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getSpecifyMovies() {
        return specifyMovies;
    }

    public void setSpecifyMovies(Byte specifyMovies) {
        this.specifyMovies = specifyMovies;
    }

    public Float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Float getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Float couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getCouponExpiration() {
        return couponExpiration;
    }

    public void setCouponExpiration(Integer couponExpiration) {
        this.couponExpiration = couponExpiration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public static Promotion assemblePromotionPO(String name, Date startTime, Date endTime, Byte specifyMovies, Float targetAmount,
                     Float couponAmount, Integer couponExpiration, String description) {
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setStartTime(startTime);
        promotion.setEndTime(endTime);
        promotion.setSpecifyMovies(specifyMovies);
        promotion.setTargetAmount(targetAmount);
        promotion.setCouponAmount(couponAmount);
        promotion.setCouponExpiration(couponExpiration);
        promotion.setDescription(description);
        return promotion;
    }
}