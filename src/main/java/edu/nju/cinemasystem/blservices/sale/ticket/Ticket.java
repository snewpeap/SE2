package edu.nju.cinemasystem.blservices.sale.ticket;

import java.util.List;

import edu.nju.cinemasystem.data.vo.Response;

public interface Ticket {

    /**
     * 锁座
     * 
     * @return
     */
    Response lockSeat(List<Integer> seatID, int userID, int arrangementID);

    /**
     * 不通过会员卡完成购票，并且给用户送优惠券
     * 
     * @return
     */
    Response payOrder(int orderID, int userID, int couponID);

    /**
     * 完成购票，并且给用户送优惠券
     * 
     * @return
     */
    Response payOrderByVIPCard(int orderID, int userID, int couponID);

    /**
     * 获取用户的购票记录
     * 
     * @param userID
     * @return
     */
    Response getTicketPurchaseRecordByUser(int userID);

    /**
     * 取消订单
     * 
     * @param userID
     * @param orderID
     * @return
     */
    Response cancelOrder(int userID, long orderID);

    /**
     * 退票，要依据退票策略哦
     * 
     * @param userID
     * @param ticketID
     * @return
     */
    Response refundTicket(int userID, int ticketID);

    /**
     * 获取用户在该排片的订单
     * @param userId
     * @param scheduleId
     * @return
     */
    Response getOrderByScheduleIdAndUserId(int userId, int scheduleId);

}
