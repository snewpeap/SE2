package edu.nju.cinemasystem.blservices.impl.movie;

import edu.nju.cinemasystem.blservices.movie.MovieLike;
import edu.nju.cinemasystem.blservices.movie.PromotionInfo;
import edu.nju.cinemasystem.blservices.movie.StatisticsInfo;
import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.data.vo.AudienceMovieVO;
import edu.nju.cinemasystem.data.vo.BaseMovieVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.movie.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovieImpl implements edu.nju.cinemasystem.blservices.movie.Movie {
    private MovieMapper movieMapper;
    private MovieLike movieLikeInfo;
    private PromotionInfo promotionInfo;
    private final StatisticsInfo statisticsInfo;

    @Autowired
    public MovieImpl(MovieMapper movieMapper, MovieLike movieLikeInfo, StatisticsInfo statisticsInfo) {
        this.movieMapper = movieMapper;
        this.movieLikeInfo = movieLikeInfo;
        this.statisticsInfo = statisticsInfo;
    }

    @Autowired
    public void setPromotionInfo(PromotionInfo promotionInfo) {
        this.promotionInfo = promotionInfo;
    }

    @Override
    public Response getMovie(@NotNull int movieID, int userID) {
        Response response = Response.success();
        if (movieID <= 0) {
            List<Movie> allMovies = movieMapper.selectAll();
            if (allMovies == null || allMovies.isEmpty()) {
                return response;
            }
            allMovies.removeIf(movie -> !movie.audienceVisible());
            List<AudienceMovieVO> allMovieVOs = new ArrayList<>(allMovies.size());
            allMovies.forEach(
                    movie -> allMovieVOs.add(assembleAudienceMovieVO(movie))
            );
            response.setContent(allMovieVOs);
        } else {
            Movie movie = movieMapper.selectByPrimaryKey(movieID);
            if (movie != null && !movie.audienceVisible()) {
                response = Response.fail();
                response.setStatusCode(403);
            } else if (movie == null) {
                response = Response.fail();
                response.setStatusCode(404);
            } else {
                AudienceMovieVO movieVO = assembleAudienceMovieVO(movie);
                movieVO.setLiked(movieLikeInfo.getIsLike(userID, movieID));
                response.setContent(movieVO);
            }
        }
        return response;
    }

    @Override
    public Response searchMovies(String query, int userID) {
        Response response = Response.success();
        if (query.isEmpty()) {
            return getMovie(0, userID);
        }
        List<Movie> movies = movieMapper.query(query);
        if (movies == null||movies.isEmpty()){
            return response;
        }
        movies.removeIf(movie -> !movie.audienceVisible());
        List<AudienceMovieVO> allMovieVOs = new ArrayList<>(movies.size());
        movies.forEach(
                movie -> allMovieVOs.add(assembleAudienceMovieVO(movie))
        );
        response.setContent(allMovieVOs);
        return response;
    }

    @Override
    public String getMovieNameByID(int movieID) {
        return movieMapper.selectByPrimaryKey(movieID).getName();
    }

    @Override
    public Date getReleaseTimeByID(int movieID) {
        return movieMapper.selectByPrimaryKey(movieID).getReleaseDate();
    }

    @Override
    public Movie getMoviePOByID(int movieID) {
        return movieMapper.selectByPrimaryKey(movieID);
    }

    @Override
    public int getDurationTimeByID(int movieID) {
        return movieMapper.selectByPrimaryKey(movieID).getDuration();
    }

    private AudienceMovieVO assembleAudienceMovieVO(Movie movie) {
        AudienceMovieVO movieVO = new AudienceMovieVO();
        BaseMovieVO.assembleMovieVO(movie, movieVO);
        int movieID = movieVO.getId();
        movieVO.setJoinedPromotions(promotionInfo.getJoinedPromotionOf(movieID));
        movieVO.setLikeNum(movieLikeInfo.getLikeAmount(movieID));
        movieVO.setHeat(statisticsInfo.getHeatOf(movieID));
        movieVO.setStatus(movie.getStatus());
        return movieVO;
    }
}
