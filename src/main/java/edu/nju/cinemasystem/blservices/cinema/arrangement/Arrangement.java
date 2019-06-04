package edu.nju.cinemasystem.blservices.cinema.arrangement;

import edu.nju.cinemasystem.data.vo.Response;

public interface Arrangement {
    /**
     * 用排片ID查找排片，排片是不可见状态的时候返回一个错误码401
     * 不用我先留着备用
     * @param aID 排片ID
     * @return 包含ArrangementVO
     */
    Response getArrangement(int aID);

    /**
     * 用电影ID查找电影的所有排片
     * @param movieID 电影ID
     * @return ArrangementVO的列表
     */
    Response getByMovieID(int movieID);

    /**
     * 获得座位分布，排片不可见的时候返回一个错误码401
     * @param aID 排片ID
     * @return 包含SeatMapVO
     */
    Response getSeatMap(int aID);
}
