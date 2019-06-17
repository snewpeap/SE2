package edu.nju.cinemasystem.data.po;

import java.util.Date;

public class MovieLike {
    private Integer userId;

    private Integer movieId;

    private Date date;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static MovieLike assembleMovieLikePO(Integer userId, Integer movieId){
        MovieLike movieLike = new MovieLike();
        movieLike.setUserId(userId);
        movieLike.setMovieId(movieId);
        movieLike.setDate(new Date());
        return movieLike;
    }
}