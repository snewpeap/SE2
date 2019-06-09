package edu.nju.cinemasystem.blservices.sale.promotion;

import java.util.List;

import edu.nju.cinemasystem.data.vo.CouponVO;
import edu.nju.cinemasystem.data.vo.Response;

public interface Coupon {

    /**
     * 获取用户可用的所有优惠券
     * @param userId
     * @return
     */
    Response getAvailableCouponsByUser(int userId);
    
    /**
     * 返回用户该次要支付的订单所适用的所有优惠券
     * @param userId
     * @param totalAmount
     * @return
     */
    List<CouponVO> getAvailableCouponsByUserAndTickets(int userId, float totalAmount);

    /**
     * 删除用户的一张优惠券
     * @param ID
     */
    void removeCouponByID(int ID);

    /**
     * 给用户送优惠券
     * @param userID
     * @param movieID
     */
    void sendCouponsToUser(int userID, int movieID);

    /**
     * 返回优惠券的优惠金额
     * @param ID
     * @return
     */
    float getCouponAmountByID(int ID);
    
}