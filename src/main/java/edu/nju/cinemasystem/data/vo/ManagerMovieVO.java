package edu.nju.cinemasystem.data.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ManagerMovieVO extends BaseMovieVO {
    private Date releaseDate;
    private Byte status;
    private int LikeNum;
    private List<Map<Date, Integer>> likeData;

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getLikeNum() {
        return LikeNum;
    }

    public void setLikeNum(int likeNum) {
        LikeNum = likeNum;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public List<Map<Date, Integer>> getLikeData() {
        return likeData;
    }

    public void setLikeData(List<Map<Date, Integer>> likeData) {
        this.likeData = likeData;
    }
}
