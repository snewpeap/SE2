package edu.nju.cinemasystem.data.vo;

import edu.nju.cinemasystem.data.po.Movie;

public class MovieVO {
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getLikeNum() {
        return LikeNum;
    }

    public void setLikeNum(int likeNum) {
        LikeNum = likeNum;
    }

    private int LikeNum;
}
