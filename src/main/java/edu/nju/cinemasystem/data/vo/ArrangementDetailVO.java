package edu.nju.cinemasystem.data.vo;

import java.util.Date;
import java.util.List;

/**
 * 排片详细信息 包含：
 * seatMap 座位图 是一个由排片座位信息构成的二维数组
 * id 排片号
 * startTime 开始时间
 * endTime 结束时间
 * fare 本场次的票价
 * hall 影厅名字
 * movie 电影的名字
 */
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
