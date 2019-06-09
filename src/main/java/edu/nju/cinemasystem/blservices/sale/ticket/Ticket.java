package edu.nju.cinemasystem.blservices.sale.ticket;

import java.util.Date;
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
    Response payOrder(long orderID, int userID, int couponID);

    /**
     * 完成购票，并且给用户送优惠券
     * 
     * @return
     */
    Response payOrderByVIPCard(long orderID, int userID, int couponID);

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
     * @return orderWithCouponVO, 如果里面的List<TicketVO> == null 则说明用户之前没有未完成订单
     */
    Response getOrderByScheduleIdAndUserId(int userId, int scheduleId);

    /**
     * 获取用户的所有电影票（不包含已退的票和未完成的票）
     * @deprecated
     * @param userId
     * @return
     */
    Response getAllTicketsByUserId(int userId);

    /**
     * 获取用户的历史消费记录，返回订单VO
     * @param userId
     * @return
     */
    Response getHistoricalConsumptionsByUserId(int userId);
}
