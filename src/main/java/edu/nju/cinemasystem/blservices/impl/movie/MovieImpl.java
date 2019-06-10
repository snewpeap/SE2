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
import java.util.List;

@Service
public class MovieImpl implements edu.nju.cinemasystem.blservices.movie.Movie {
    private MovieMapper movieMapper;
    private MovieLike movieLikeInfo;
    private PromotionInfo promotionInfo;
    private final StatisticsInfo statisticsInfo;

    @Autowired
    public MovieImpl(MovieMapper movieMapper, MovieLike movieLikeInfo, PromotionInfo promotionInfo, StatisticsInfo statisticsInfo) {
        this.movieMapper = movieMapper;
        this.movieLikeInfo = movieLikeInfo;
        this.promotionInfo = promotionInfo;
        this.statisticsInfo = statisticsInfo;
    }

    @Override
    public Response getMovie(@NotNull int movieID) {
        Response response = Response.success();
        if (movieID <= 0) {
            List<Movie> allMovies = movieMapper.selectAll();
            List<AudienceMovieVO> allMovieVOs = new ArrayList<>(allMovies.size());
            allMovies.forEach(
                    movie -> allMovieVOs.add(assembleAudienceMovieVO(movie))
            );
            response.setContent(allMovieVOs);
        } else {
            Movie movie = movieMapper.selectByPrimaryKey(movieID);
            if (movie != null && !movie.getStatusBoolean()) {
                response = Response.fail();
                response.setStatusCode(401);
            } else if (movie != null) {
                AudienceMovieVO movieVO = assembleAudienceMovieVO(movie);
                response.setContent(movieVO);
            } else {
                response = Response.fail();
                response.setStatusCode(404);
            }
        }
        return response;
    }

    @Override
    public Response searchMovies(String query) {
        //TODO
        return null;
    }

    @Override
    public String getMovieNameByID(int movieID) {
        //TODO
        return null;
    }

    private AudienceMovieVO assembleAudienceMovieVO(Movie movie){
        AudienceMovieVO movieVO = (AudienceMovieVO) BaseMovieVO.assembleMovieVO(movie);
        int movieID = movieVO.getId();
        movieVO.setJoinedPromotions(promotionInfo.getJoinedPromotionOf(movieID));
        movieVO.setLikeNum(movieLikeInfo.getLikeAmount(movieID));
        movieVO.setHeat(statisticsInfo.getHeatOf(movieID));
        return movieVO;
    }
}
