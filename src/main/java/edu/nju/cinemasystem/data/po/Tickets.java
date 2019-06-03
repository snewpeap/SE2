package edu.nju.cinemasystem.data.po;

import java.util.Date;

public class Tickets {
    private Integer id;

    private Integer userId;

    private Integer arrangementId;

    private Integer seatId;

    private Date date;

    private Byte status;

    private Float realAmount;

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
}