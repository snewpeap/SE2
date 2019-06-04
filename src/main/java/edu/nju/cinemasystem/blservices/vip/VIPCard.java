package edu.nju.cinemasystem.blservices.vip;

import edu.nju.cinemasystem.data.vo.Response;

public interface VIPCard {
    /**
     * 获得VIP卡信息
     * @param userID 用户ID也是卡ID
     * @return VIP卡VO
     */
    Response getVIPCard(int userID);

    /**
     * 购买会员卡
     * @param userID 购买用户
     * @return 买成功的VIP卡VO
     */
    Response buyVIPCard(int userID);

    /**
     * 获得充值优惠策略，满减这些的
     * @return 优惠策略VO列表
     */
    Response getRechargeReduction();

    /**
     * 获得充值记录
     * @param userID 用户ID也是卡ID
     * @return 充值记录VO列表
     */
    Response getRechargeHistory(int userID);
}
