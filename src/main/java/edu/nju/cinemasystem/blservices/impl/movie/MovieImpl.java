package edu.nju.cinemasystem.blservices.impl.movie;

import edu.nju.cinemasystem.blservices.movie.MovieLike;
import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.data.vo.MovieVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.movie.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class MovieImpl implements edu.nju.cinemasystem.blservices.movie.Movie {
    private MovieMapper movieMapper;
    private MovieLike movieLikeInfo;

    @Autowired
    public MovieImpl(MovieMapper movieMapper, MovieLike movieLikeInfo) {
        this.movieMapper = movieMapper;
        this.movieLikeInfo = movieLikeInfo;
    }

    @Override
    public Response getMovie(@NotNull int movieID) {
        Response response = Response.success();
        Movie movie = movieMapper.selectByPrimaryKey(movieID);
        if (!movie.getStatusBoolean()){
            response = Response.fail();
            response.setStatusCode(401);
            return response;
        }else {
            MovieVO movieVO = new MovieVO();
            response.setStatusCode(200);
            //TODO
            return response;
        }
    }

    @Override
    public Response searchMovies(String query) {
        return null;
    }
}
