package edu.nju.cinemasystem.blservices.movie;

import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.MovieForm;

import java.util.List;

public interface MovieManagement {
    Response addMovie(MovieForm movieForm);
    Response modifyMovie(MovieForm movieForm);
    Response removeMovie(int movieID);
    Response downwardMovie(int movieID);

    /**
     * 负数代表查询所有电影
     * @param movieID 电影ID
     * @return 为正整数返回一部电影；为非正数返回所有电影（包括已下架电影）
     */
    Response getMovie(int movieID);

    /**
     * 获取所有已上映和已下架的电影
     * @return 所有已上映和已下架的电影
     */
    List<Movie> getReleasedMovies();

    Movie getMovieByID(int movieID);
}
