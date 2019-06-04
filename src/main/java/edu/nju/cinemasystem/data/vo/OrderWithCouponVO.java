package edu.nju.cinemasystem.data.vo;

import java.util.List;

public class OrderWithCouponVO {
     
    private int ID;
    private List<TicketVO> tickets;
    private List<CouponVO> coupons;

    public int getID() {
        return ID;
    }

    public List<CouponVO> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponVO> coupons) {
        this.coupons = coupons;
    }

    public List<TicketVO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketVO> tickets) {
        this.tickets = tickets;
    }

    public void setID(int iD) {
        this.ID = iD;
    }
    

}