package edu.nju.cinemasystem.blservices.vip;

import edu.nju.cinemasystem.data.vo.RechargeReductionVO;
import edu.nju.cinemasystem.data.vo.Response;

import java.util.List;

public interface VIPManagement {
    /**
     * 拿到全部VIP用户
     * @return UserVO列表
     */
    Response getVIPs();

    /**
     * 筛选总计消费在money元以上的VIP用户
     * @param money 钱数
     * @return UserVO列表
     */
    Response getVIPs(double money);

    Response presentCoupon(List<Integer> vips, List<Integer> couponIDs);

    Response addReduction(RechargeReductionVO reductionVO);

    Response modifyReduction(RechargeReductionVO reductionVO);

    Response removeReduction(int id);
}
