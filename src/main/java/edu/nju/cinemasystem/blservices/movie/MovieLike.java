package edu.nju.cinemasystem.blservices.movie;

import edu.nju.cinemasystem.data.vo.Response;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MovieLike {
    Response like(int userID, int movieID);

    Response unlike(int userID, int movieID);

    int getLikeAmount(int movieID);

    boolean getIsLike(int userID, int movieID);

    List<Map<Date, Integer>> getLikeDataOf(int movieID);
}
