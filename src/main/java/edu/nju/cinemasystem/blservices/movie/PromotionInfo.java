package edu.nju.cinemasystem.blservices.movie;

import java.util.List;

public interface PromotionInfo {
    /**
     * 给你一个电影ID，返回这个电影参与的所有优惠活动ID
     * @param movieID 目标电影ID
     * @return 优惠活动ID列表
     */
    List<Integer> getJoinedPromotionOf(int movieID);
}
