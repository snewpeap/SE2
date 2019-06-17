package edu.nju.cinemasystem.data.vo;

import java.util.Date;
import java.util.List;

public class ArrangementDetailVO {
    private List<ArrangementSeatVO[]> seatMap;

    private Integer id;

    private Date startTime;

    private Date endTime;

    private Float fare;

    private String hall;

    private String movie;

    public List<ArrangementSeatVO[]> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(List<ArrangementSeatVO[]> seatMap) {
        this.seatMap = seatMap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Float getFare() {
        return fare;
    }

    public void setFare(Float fare) {
        this.fare = fare;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
