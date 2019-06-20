package edu.nju.cinemasystem.blservices.impl.movie;

import edu.nju.cinemasystem.blservices.movie.MovieLikeService;
import edu.nju.cinemasystem.data.po.Movie;
import edu.nju.cinemasystem.data.po.MovieLike;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.movie.MovieLikeMapper;
import edu.nju.cinemasystem.dataservices.movie.MovieMapper;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MovieLikeImpl implements MovieLikeService {
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
        MovieLike record = MovieLike.assembleMovieLikePO(userID, movieID);
        if (movieLikeMapper.selectByUserAndMovie(record) != null) {
            if (movieLikeMapper.deleteByUserAndMovie(record) == 0) {
                response = Response.fail();
                response.setMessage(globalMsg.getOperationFailed());
                response.setContent(true);
            } else {
                response.setContent(false);
            }
        }
        movieLikeMapper.insertSelective(record);
        response = Response.success();
        response.setContent(true);
        return response;
    }

    @Override
    public Response unlike(int userID, int movieID) {
        Response response = Response.success();
        MovieLike record = MovieLike.assembleMovieLikePO(userID, movieID);
        if (movieLikeMapper.selectByUserAndMovie(record) == null) {
            record.setDate(new Date());
            if (movieLikeMapper.insert(record) == 0) {
                response = Response.fail();
                response.setMessage(globalMsg.getOperationFailed());
                response.setContent(false);
            } else {
                response.setContent(true);
            }
        }
        movieLikeMapper.deleteByUserAndMovie(record);
        response = Response.success();
        response.setContent(false);
        return response;
    }

    @Override
    public int getLikeAmount(int movieID) {
        return movieLikeMapper.selectByMovieID(movieID).size();
    }

    @Override
    public boolean getIsLike(int userID, int movieID) {
        return movieLikeMapper.selectByUserAndMovie(MovieLike.assembleMovieLikePO(userID, movieID)) != null;
    }

    @Override
    public List<Map<String, Object>> getLikeDataOf(int movieID) {
        List<Map<String, Object>> rawDate = movieLikeMapper.selectByMovieGroupByDate(movieID);
        Movie movie = movieMapper.selectByPrimaryKey(movieID);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = movie.getReleaseDate();
        try {
            startDate = sdf.parse(sdf.format(startDate));
        } catch (ParseException ignored) {

        }
        final Date theStart = startDate;
        Date endDate = new Date();
        endDate = endDate.before(movie.getStartDate()) ? endDate : movie.getStartDate();
        final Date theEnd = endDate;

        rawDate.removeIf(perDayData -> {
            Date date = (Date) perDayData.get("date");
            return date == null || date.before(theStart) || date.compareTo(theEnd) >= 0;
        });
        return rawDate;
    }
}
