package edu.nju.cinemasystem.blservices.vip;

import edu.nju.cinemasystem.data.vo.Response;

public interface VIPCard {
    /**
     * 获得VIP卡信息，用户没有会员卡的时候提示购买会员卡
     * @param userID 用户ID也是卡ID
     * @return VIP卡VO
     */
    Response getVIPCard(int userID);

    /**
     * 添加会员卡，调用这个方法的前提是第三方支付已经成功了，不成功请勿调用
     * @param userID 购买用户
     * @return 买成功的VIP卡VO
     */
    Response addVIPCard(int userID);

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

    /**
     * 给会员卡退款时调用
     * @param userID
     * @param amount 退款的总额
     * @return
     */
    Response addVIPBalance(int userID, float amount);

    Response deposit(int userID, float amount);

    Response pay(int userID, float amount);
}
