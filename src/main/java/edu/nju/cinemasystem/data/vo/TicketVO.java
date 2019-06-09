package edu.nju.cinemasystem.data.vo;

import java.util.Date;


public class TicketVO {

    private Integer id;

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    private long orderID;

    private Integer userId;

    private Integer arrangementId;

    private Date startDate;

    private Date endDate;

    private String status;

    private Float realAmount;

    private int row;

    private int column;

    private String hallName;

    public TicketVO(Integer id, long orderID,Integer userId, Integer arrangementId, Date startDate, Date endDate, String status, Float realAmount, int row, int column, String hallName) {
        this.id = id;
        this.orderID = orderID;
        this.userId = userId;
        this.arrangementId = arrangementId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.realAmount = realAmount;
        this.row = row;
        this.column = column;
        this.hallName = hallName;
    }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }
}