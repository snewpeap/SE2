package edu.nju.cinemasystem.data.vo;

import java.util.Date;
import java.util.List;

public class OrderVO {

    private Long orderID;
    private List<TicketVO> ticketVOs;
    private float realSpend;
    private float originalSpend;
    private Date date;

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

    public OrderVO(Long orderID, List<TicketVO> ticketVOs, float realSpend, float originalSpend, Date date) {
        this.orderID = orderID;
        this.ticketVOs = ticketVOs;
        this.realSpend = realSpend;
        this.originalSpend = originalSpend;
        this.date = date;
    }
}
