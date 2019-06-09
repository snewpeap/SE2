package edu.nju.cinemasystem.blservices.vip;

import edu.nju.cinemasystem.util.exception.ServiceException;

import java.util.List;

public interface VIPCouponBusiness {
    /**
     * 为用户赠送优惠券id列表里的优惠券
     * 如果赠送过程出现问题，抛出ServiceException
     *
     * @param userID 用户id
     * @param promotionIDs 活动id列表
     * @throws ServiceException 赠送失败时，在异常信息中分别指明赠送成功和失败的优惠券id
     */
    void presentCouponTo(int userID, List<Integer> promotionIDs) throws ServiceException;
}
