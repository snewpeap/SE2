package edu.nju.cinemasystem.blservices.movie;

import edu.nju.cinemasystem.data.vo.Response;

public interface Movie {
    /**
     * 负数代表查询所有电影
     * @param movieID 电影ID
     * @return 为正整数返回一部电影；为非正数返回所有电影
     */
    Response getMovie(int movieID);
    Response searchMovies(String query);
}
