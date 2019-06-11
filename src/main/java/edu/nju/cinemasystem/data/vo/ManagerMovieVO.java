package edu.nju.cinemasystem.data.vo;

public class ManagerMovieVO extends BaseMovieVO {
    private boolean status;
    private int LikeNum;

    public int getLikeNum() {
        return LikeNum;
    }

    public void setLikeNum(int likeNum) {
        LikeNum = likeNum;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
