package edu.nju.cinemasystem.data.vo;

import java.util.List;

public class AudienceMovieVO extends BaseMovieVO {
    private Byte status;
    private List<Integer> joinedPromotions;
    private int LikeNum;
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
