package edu.nju.cinemasystem.data.vo.form;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

public class PromotionForm {

    @NotNull
    private String name;

    @NotNull
    @Future
    private Date startTime;

    @NotNull
    @Future
    private Date endTime;

    private Boolean specifyMovies;

    @NotNull
    @PositiveOrZero
    private Float targetAmount;

    @NotNull
    @Positive
    private Float couponAmount;

    @NotNull
    @Positive
    private Integer couponExpiration;

    @NotNull
    private String description;

    private List<Integer> movieIDs;

    public List<Integer> getMovieIDs() {
        return movieIDs;
    }

    public void setMovieIDs(List<Integer> movieIDs) {
        this.movieIDs = movieIDs;
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