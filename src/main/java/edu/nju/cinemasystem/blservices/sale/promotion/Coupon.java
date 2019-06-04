package edu.nju.cinemasystem.blservices.sale.promotion;

import edu.nju.cinemasystem.data.vo.Response;

public interface Coupon {

    /**
     * TODO：获取用户可用的所有优惠券
     * @param userId
     * @return
     */
    Response getAvailableCouponsByUser(int userId);
    
}