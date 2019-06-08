package edu.nju.cinemasystem.blservices.impl.movie;

import edu.nju.cinemasystem.data.po.MovieLike;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.dataservices.movie.MovieLikeMapper;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MovieLikeImpl implements edu.nju.cinemasystem.blservices.movie.MovieLike {
    private final MovieLikeMapper movieLikeMapper;
    private final GlobalMsg globalMsg;

    @Autowired
    public MovieLikeImpl(MovieLikeMapper movieLikeMapper, GlobalMsg globalMsg) {
        this.movieLikeMapper = movieLikeMapper;
        this.globalMsg = globalMsg;
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
}
