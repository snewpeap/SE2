package edu.nju.cinemasystem.blservices.sale.ticket;

import com.alipay.api.AlipayApiException;
import edu.nju.cinemasystem.data.vo.Response;

import java.util.List;

public interface TicketService {

    /**
     * 锁座
     * 
     * @return orderWithCouponVO
     */
    Response lockSeat(List<Integer> seatID, int userID, int arrangementID);

    Response payable(long orderID, int couponID, int userID);

    String requestAlipay(long orderID) throws AlipayApiException;

    Response payOrder(long orderID, float amount);

    /**
     * 通过会员卡，完成购票，并且给用户送优惠券
     * 
     * @return 是否成功
     */
    Response payOrderByVIPCard(long orderID, int userID);

    /**
     * 用户支付宝同步返回时检查订单完成状态
     * @param orderID
     * @return
     */
    Response getOrderStatus(long orderID);

    /**
     * 取消订单
     * 
     * @param userID 用户ID
     * @param orderID 订单ID
     * @return 是否成功
     */
    Response cancelOrder(int userID, long orderID);

    /**
     * 退票，要依据退票策略哦
     * 
     * @param userID 用户ID
     * @param ticketID 票的ID
     * @return 是否成功
     */
    Response refundTicket(int userID, int ticketID);

    /**
     * 获取用户在该排片的订单
     * @param userId 用户ID
     * @param scheduleId 排片ID
     * @return orderWithCouponVO, 如果里面的List<TicketVO> == null 则说明用户之前没有未完成订单
     */
    Response getOrderByScheduleIdAndUserId(int userId, int scheduleId);

    /**
     * 获取用户的所有电影票（不包含已退的票和未完成的票）
     * @deprecated
     * @param userId 用户ID
     * @return 票的list
     */
    Response getAllTicketsByUserId(int userId);

    /**
     * 获取用户的历史消费记录
     * @param userId 用户ID
     * @return List<OrderVO>
     */
    Response getHistoricalConsumptionsByUserId(int userId);
}
