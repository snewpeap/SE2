package edu.nju.cinemasystem.blservices.movie;

import edu.nju.cinemasystem.data.vo.Response;

public interface MovieLike {
    Response like(int userID, int movieID);

    Response unlike(int userID, int movieID);

    int getLikeAmount(int movieID);
}
