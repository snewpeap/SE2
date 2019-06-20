package edu.nju.cinemasystem.data.vo;

import java.util.Date;

import edu.nju.cinemasystem.data.po.Arrangement;

/**
 * 排片基本信息 和 详细信息主要差别在没有座位图和有可见日期
 * id 排片号
 * startTime 开始时间
 * endTime 结束时间
 * fare 场次票价
 * hallId 影厅号
 * movieId 电影号
 * visibleDate 可见日期
 * hallName 影厅名称
 */
public class ArrangementVO {
    private Integer id;

    private Date startTime;

    private Date endTime;

    private Float fare;

    private Integer hallId;

    private Integer movieId;

    private Date visibleDate;

    private String hallName;

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

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public ArrangementVO(Arrangement arrangement) {
        this.id = arrangement.getId();
        this.startTime = arrangement.getStartTime();
        this.endTime = arrangement.getEndTime();
        this.fare = arrangement.getFare();
        this.hallId = arrangement.getHallId();
        this.movieId = arrangement.getMovieId();
        this.visibleDate = arrangement.getVisibleDate();
    }
}