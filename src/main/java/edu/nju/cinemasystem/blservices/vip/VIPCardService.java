package edu.nju.cinemasystem.blservices.vip;

import edu.nju.cinemasystem.data.vo.Response;

public interface VIPCardService {
    /**
     * 获得VIP卡信息，用户没有会员卡的时候提示购买会员卡
     * @param userID 用户ID也是卡ID
     * @return VIP卡VO
     */
    Response getVIPCard(int userID);

    Response buyable(int userID);

    /**
     * 添加会员卡，调用这个方法的前提是第三方支付已经成功了，不成功请勿调用
     * @param userID 购买用户
     * @return 买成功的VIP卡VO
     */
    Response addVIPCard(int userID, String orderID);

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
     * @param userID 用户id
     * @param amount 退款的总额
     * @return 结果
     */
    Response addVIPBalance(int userID, float amount);

    /**
     * 检查是否能进行本次充值
     * 检查项：
     * 1. 是否有会员卡
     * 2. amount非负
     * 3. 之前没有未支付的订单
     * 若检查通过会返回带有实付金额的vip.DelayedTask对象
     * 若检查失败，若有未支付的订单，会返回未支付订单的DelayedTask对象
     *
     * @param userID 用户id
     * @param amount 要充值的原始金额
     * @return 检查的结果
     */
    Response depositable(int userID, float amount);

    Response deposit(int userID, String orderID);

    Response pay(int userID, float amount);
}
