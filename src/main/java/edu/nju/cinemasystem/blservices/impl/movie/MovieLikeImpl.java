package edu.nju.cinemasystem.blservices.impl.movie;

import edu.nju.cinemasystem.blservices.movie.MovieLike;
import edu.nju.cinemasystem.data.vo.Response;
import org.springframework.stereotype.Service;

@Service
public class MovieLikeImpl implements MovieLike {
    @Override
    public Response like(int userID, int movieID) {
        return null;
    }

    @Override
    public Response unlike(int userID, int movieID) {
        return null;
    }

    @Override
    public int getLikeAmount(int movieID) {
        return 0;
    }
}
