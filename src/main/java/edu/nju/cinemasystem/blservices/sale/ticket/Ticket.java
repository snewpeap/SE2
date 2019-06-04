package edu.nju.cinemasystem.blservices.sale.ticket;

import java.util.List;

import edu.nju.cinemasystem.data.vo.Response;

public interface Ticket {

    /**
     * TODO：锁座
     * 
     * @return
     */
    Response lockSeat(List<Integer> seatID, int userID, int arrangementID);

    /**
     * TODO：不通过会员卡完成购票，并且给用户送优惠券
     * 
     * @return
     */
    Response payOrder(int orderID, int userID, int couponID);

    /**
     * TODO：完成购票，并且给用户送优惠券
     * 
     * @return
     */
    Response payOrderByVIPCard(int orderID, int userID, int couponID);

    /**
     * TODO：获取用户的购票记录
     * 
     * @param userID
     * @return
     */
    Response getTicketPurchaseRecordByUser(int userID);

    /**
     * TODO：取消订单
     * 
     * @param userID
     * @param orderID
     * @return
     */
    Response cancelOrder(int userID, long orderID);

    /**
     * TODO：退票，要依据退票策略哦
     * 
     * @param userID
     * @param ticketID
     * @return
     */
    Response refundTicket(int userID, int ticketID);

}
