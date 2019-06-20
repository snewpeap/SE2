package edu.nju.cinemasystem.data.vo;

import java.util.Date;
import java.util.List;

public class OrderVO {

    private Long orderID;
    private List<TicketVO> ticketVOs;
    private float realSpend;
    private float originalSpend;
    private Date completeTime;
    private Date startTime;
    private Date endTime;
    private String movieName;
    private String hallName;
    private int movieId;

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public List<TicketVO> getTicketVOs() {
        return ticketVOs;
    }

    public void setTicketVOs(List<TicketVO> ticketVOs) {
        this.ticketVOs = ticketVOs;
    }

    public float getRealSpend() {
        return realSpend;
    }

    public void setRealSpend(float realSpend) {
        this.realSpend = realSpend;
    }

    public float getOriginalSpend() {
        return originalSpend;
    }

    public void setOriginalSpend(float originalSpend) {
        this.originalSpend = originalSpend;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
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

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public OrderVO(Long orderID, List<TicketVO> ticketVOs, float realSpend, float originalSpend, Date completeTime, Date startTime, Date endTime, String movieName, String hallName,int movieId) {
        this.orderID = orderID;
        this.ticketVOs = ticketVOs;
        this.realSpend = realSpend;
        this.originalSpend = originalSpend;
        this.completeTime = completeTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.movieName = movieName;
        this.hallName = hallName;
        this.movieId = movieId;
    }
}
