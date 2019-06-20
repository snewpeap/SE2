package edu.nju.cinemasystem.data.vo;

import java.util.Date;
import java.util.List;

/**
 * 包含优惠券的订单信息 包括：
 * ID 订单序号
 * tickets 电影票列表 里面是TicketVO
 * coupons 优惠券列表 里面是CouponVO
 * startTime 电影开始时间
 * endTime 电影结束时间
 * movieName 电影名字
 * hallName 影厅名字
 */
public class OrderWithCouponVO {

    private Long ID;
    private List<TicketVO> tickets;
    private List<CouponVO> coupons;
    private Date startTime;
    private Date endTime;
    private String movieName;
    private String hallName;

    public OrderWithCouponVO(Long ID, List<TicketVO> tickets, List<CouponVO> coupons, Date startTime, Date endTime, String movieName, String hallName) {
        this.ID = ID;
        this.tickets = tickets;
        this.coupons = coupons;
        this.startTime = startTime;
        this.endTime = endTime;
        this.movieName = movieName;
        this.hallName = hallName;
    }

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
}