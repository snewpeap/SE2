package edu.nju.cinemasystem.blservices.movie;

import java.util.List;

public interface SaleInfo {
    /**
     * 给你一个电影ID，返回这个电影参与的所有优惠活动ID
     * @param movieID 目标电影ID
     * @return 优惠活动ID列表
     */
    List<Integer> getJoinedPromotionOf(int movieID);

    /**
     * 给一个电影ID，期望得到电影的热度，根据票房来算，但是最好不要透露真正的票房，当然是真实票房也没关系了
     * @param movieID 电影id
     * @return 热度值
     */
    double getHeatOf(int movieID);
}
