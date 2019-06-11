package edu.nju.cinemasystem.data.vo.form;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class ArrangementForm {

    @NotNull
    @Future
    private Date startTime;

    @NotNull
    @Future
    private Date endTime;

    @NotNull
    @Positive
    private Float fare;

    @NotNull
    private Integer hallId;

    @NotNull
    private Integer movieId;

    @Future
    private Date visibleDate;

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

    public Float getFare() {
        return fare;
    }

    public void setFare(Float fare) {
        this.fare = fare;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Date getVisibleDate() {
        return visibleDate;
    }

    public void setVisibleDate(Date visibleDate) {
        this.visibleDate = visibleDate;
    }
}
