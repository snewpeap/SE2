package edu.nju.cinemasystem.data.vo.Form;

import java.util.Date;

public class PromotionForm {
    
    private String name;

    private Date startTime;

    private Date endTime;

    private Boolean specifyMovies;

    private Float targetAmount;

    private Float couponAmount;

    private Integer couponExpiration;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getSpecifyMovies() {
        return specifyMovies;
    }

    public void setSpecifyMovies(Boolean specifyMovies) {
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
        this.description = description;
    }

}