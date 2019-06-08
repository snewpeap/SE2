package edu.nju.cinemasystem.data.vo;

import java.util.Date;

import edu.nju.cinemasystem.data.po.Ticket;

public class TicketVO {

    private Integer id;

    private Integer userId;

    private Integer arrangementId;

    private Integer seatId;

    private Date date;

    private String status;

    private Float realAmount;

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Float realAmount) {
        this.realAmount = realAmount;
    }

    public TicketVO(Ticket ticket) {
        this.id = ticket.getId();
        this.orderID = ticket.getOrderID();
        this.userId = ticket.getUserId();
        this.arrangementId = ticket.getArrangementId();
        this.seatId = ticket.getSeatId();
        this.date = ticket.getDate();
        switch(ticket.getStatus()){
            case (byte)0:
                this.status = "未完成";
                break;
            case (byte)1:
                this.status = "已完成";
                break;
            case (byte)2:
                this.status = "已失效";
                break;
            case (byte)3:
                this.status = "已退票";
                break;
            default:
                break;
        }
        this.realAmount = ticket.getRealAmount();
    }
    
}