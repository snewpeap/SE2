package edu.nju.cinemasystem.blservices.impl.movie;

import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.data.po.MovieLike;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.movie.MovieLikeMapper;
import edu.nju.cinemasystem.dataservices.movie.MovieMapper;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class MovieLikeImpl implements edu.nju.cinemasystem.blservices.movie.MovieLike {
    private final MovieLikeMapper movieLikeMapper;
    private final MovieMapper movieMapper;
    private final GlobalMsg globalMsg;

    @Autowired
    public MovieLikeImpl(MovieLikeMapper movieLikeMapper, GlobalMsg globalMsg, MovieMapper movieMapper) {
        this.movieLikeMapper = movieLikeMapper;
        this.globalMsg = globalMsg;
        this.movieMapper = movieMapper;
    }

    @Override
    public Response like(int userID, int movieID) {
        Response response = Response.success();
        MovieLike record = new MovieLike(userID, movieID);
        if (movieLikeMapper.selectByUserAndMovie(record) != null) {
            if (movieLikeMapper.deleteByUserAndMovie(record) == 0) {
                response = Response.fail();
                response.setMessage(globalMsg.getOperationFailed());
            }
        }
        return response;
    }

    @Override
    public Response unlike(int userID, int movieID) {
        Response response = Response.success();
        MovieLike record = new MovieLike(userID, movieID);
        if (movieLikeMapper.selectByUserAndMovie(record) == null) {
            record.setDate(new Date());
            if (movieLikeMapper.insert(record) == 0) {
                response = Response.fail();
                response.setMessage(globalMsg.getOperationFailed());
            }
        }
        return response;
    }

    @Override
    public int getLikeAmount(int movieID) {
        return movieLikeMapper.selectByMovieID(movieID).size();
    }

    @Override
    public boolean getIsLike(int userID, int movieID) {
        return movieLikeMapper.selectByUserAndMovie(new MovieLike(userID, movieID)) != null;
    }

    @Override
    public List<Map<Date, Integer>> getLikeDataOf(int movieID) {
        List<Map<Date, Integer>> rawDate = movieLikeMapper.selectByMovieGroupByDate(movieID);
        Movie movie = movieMapper.selectByPrimaryKey(movieID);
        Date startDate = movie.getReleaseDate();
        Date endDate = new Date();
        endDate = endDate.before(movie.getStartDate()) ? endDate : movie.getStartDate();
        final Date theEnd = endDate;
        rawDate.removeIf(preDayData -> {
            Iterator<Map.Entry<Date, Integer>> iterator = preDayData.entrySet().iterator();
            Date date = null;
            while (iterator.hasNext()) {
                date = iterator.next().getKey();
            }
            return date==null||date.before(startDate)||date.after(theEnd);
        });
        return rawDate;
    }
}
