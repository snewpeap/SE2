package edu.nju.cinemasystem.data.vo;

import java.util.List;

public class AudienceMovieVO extends BaseMovieVO {
    private List<Integer> joinedPromotions;
    private int LikeNum;
    private double heat;

    public double getHeat() {
        return heat;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    public int getLikeNum() {
        return LikeNum;
    }

    public void setLikeNum(int likeNum) {
        LikeNum = likeNum;
    }

    public List<Integer> getJoinedPromotions() {
        return joinedPromotions;
    }

    public void setJoinedPromotions(List<Integer> joinedPromotions) {
        this.joinedPromotions = joinedPromotions;
    }
}
