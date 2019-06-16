package edu.nju.cinemasystem.data.vo;

import java.util.Date;
import java.util.List;

import edu.nju.cinemasystem.data.po.Promotion;

public class PromotionVO {
    private Integer id;

    private String name;

    private Date startTime;

    private Date endTime;

    private List<String> movieList;

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

    public List<String> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<String> movieList) {
        this.movieList = movieList;
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

    public PromotionVO(Promotion promotion) {
        this.id = promotion.getId();
        this.name = promotion.getName();
        this.startTime = promotion.getStartTime();
        this.endTime = promotion.getEndTime();
        this.targetAmount = promotion.getTargetAmount();
        this.couponAmount = promotion.getCouponAmount();
        this.couponExpiration = promotion.getCouponExpiration();
        this.description = promotion.getDescription();
    }
}