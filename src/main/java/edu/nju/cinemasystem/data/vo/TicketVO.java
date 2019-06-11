package edu.nju.cinemasystem.data.vo;

public class TicketVO {

    private Integer id;

    private long orderID;

    private Integer userId;

    private Integer arrangementId;

    private String status;

    private Float realAmount;

    private int row;

    private int column;


    public TicketVO(Integer id, long orderID, Integer userId, Integer arrangementId, String status, Float realAmount, int row, int column) {
        this.id = id;
        this.orderID = orderID;
        this.userId = userId;
        this.arrangementId = arrangementId;
        this.status = status;
        this.realAmount = realAmount;
        this.row = row;
        this.column = column;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
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
}