package edu.nju.cinemasystem.data.po;

import java.util.Date;

public class Ticket {
    private Integer id;

    private Integer userId;

    private Integer arrangementId;

    private Integer seatId;

    private Date date;

    private Byte status; //0,1,2,3分别是未完成，已完成，已失效，已退票

    private Float realAmount;

    private Long orderID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Integer arrangementId) {
        this.arrangementId = arrangementId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Float getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Float realAmount) {
        this.realAmount = realAmount;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Ticket(int userId, int arrangementId, int seatId, Date date, Byte status, Float realAmount,
            Long orderID) {
        this.userId = userId;
        this.arrangementId = arrangementId;
        this.seatId = seatId;
        this.date = date;
        this.status = status;
        this.realAmount = realAmount;
        this.orderID = orderID;
    }
}