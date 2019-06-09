package edu.nju.cinemasystem.data.vo;

import java.util.List;

public class OrderWithCouponVO {
     
    private Long ID;
    private List<TicketVO> tickets;
    private List<CouponVO> coupons;

    public Long getID() {
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

    public void setID(Long iD) {
        this.ID = iD;
    }

    public OrderWithCouponVO(Long iD, List<TicketVO> tickets, List<CouponVO> coupons) {
        ID = iD;
        this.tickets = tickets;
        this.coupons = coupons;
    }
    
    

}