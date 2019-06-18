package edu.nju.cinemasystem.blservices.sale.promotion;

import edu.nju.cinemasystem.data.vo.CouponVO;
import edu.nju.cinemasystem.data.vo.Response;

import java.util.List;

public interface CouponService {

    /**
     * 获取用户可用的所有优惠券
     * @param userId 用户ID
     * @return List<CouponVO>
     */
    Response getAvailableCouponsByUser(int userId);
    
    /**
     * 返回用户该次要支付的订单所适用的所有优惠券
     * @param userId 用户ID
     * @param totalAmount 总金额
     * @return List<CouponVO>
     */
    List<CouponVO> getAvailableCouponsByUserAndTickets(int userId, float totalAmount);

    /**
     * 删除用户的一张优惠券
     * @param ID 用户ID
     */
    void removeCouponByID(int ID);

    /**
     * 给用户送优惠券
     * @param userID 用户ID
     * @param movieID 电影ID
     */
    void sendCouponsToUser(int userID, int movieID);

    /**
     * 返回优惠券的优惠金额
     * @param ID 优惠券ID
     * @return 优惠金额
     */
    float getCouponAmountByID(int ID);
    
}