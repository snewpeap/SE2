package edu.nju.cinemasystem.blservices.movie;

import edu.nju.cinemasystem.data.vo.MovieForm;
import edu.nju.cinemasystem.data.vo.Response;

public interface MovieManagement {
    Response addMovie(MovieForm movieForm);
    Response modifyMovie(MovieForm movieForm);
    Response removeMovie(int movieID);

    /**
     * 负数代表查询所有电影
     * @param movieID 电影ID
     * @return 为正整数返回一部电影；为非正数返回所有电影（包括已下架电影）
     */
    Response getMovie(int movieID);
}
