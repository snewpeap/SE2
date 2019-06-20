package edu.nju.cinemasystem.data.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 给经理的电影信息 包含：
 * releaseDate 上映日期
 * status 状态：0-未上映 1-热映中 2-已下映 3-已下架
 * LikeNum 想看人数
 * likeData 想看数据 是一个list 每一项是一个map key是string
 *
 */
public class ManagerMovieVO extends BaseMovieVO {
    private Date releaseDate;
    private Byte status;
    private int LikeNum;
    private List<Map<String, Object>> likeData;

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

    public List<Map<String, Object>> getLikeData() {
        return likeData;
    }

    public void setLikeData(List<Map<String, Object>> likeData) {
        this.likeData = likeData;
    }
}
