package edu.nju.cinemasystem.blservices.movie;

import edu.nju.cinemasystem.data.vo.Response;

import java.util.Date;


public interface MovieService {
    /**
     * 非正数代表查询所有电影
     * @param movieID 电影ID
     * @param userID 用户id
     * @return 为正整数返回一部电影；为非正数返回所有电影列表
     */
    Response getMovie(int movieID, int userID);

    /**
     * 查找电影，关键字我也不知道能查到啥
     * @param query 查询关键字
     * @return 查到的电影列表
     */
    Response searchMovies(String query,int userID);

    /**
     * 通过电影ID获取电影名称
     * @param movieID 电影ID
     * @return 电影名称
     */
    String getMovieNameByID(int movieID);

    Date getReleaseTimeByID(int movieID);

    edu.nju.cinemasystem.data.po.Movie getMoviePOByID(int movieID);

    int getDurationTimeByID(int movieID);
}
