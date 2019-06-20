package edu.nju.cinemasystem.data.vo;

import java.util.List;

/**
 * 给观众的电影信息 继承自 BaseMovieVO 包含：
 * status 状态：0-未上映 1-热映中 2-已下映 3-已下架
 * joinedPromotions 加入的活动
 * likeNum 想看人数
 * isLiked 这个观众是否想看了这个电影
 * heat 热度
 */
public class AudienceMovieVO extends BaseMovieVO {
    private Byte status;
    private List<String> joinedPromotions;
    private int likeNum;
    private boolean isLiked;
    private double heat;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public double getHeat() {
        return heat;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public List<String> getJoinedPromotions() {
        return joinedPromotions;
    }

    public void setJoinedPromotions(List<String> joinedPromotions) {
        this.joinedPromotions = joinedPromotions;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
