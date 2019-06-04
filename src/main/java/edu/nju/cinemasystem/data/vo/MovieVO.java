package edu.nju.cinemasystem.data.vo;

import edu.nju.cinemasystem.data.po.Movie;

import java.util.List;

public class MovieVO {
    private Movie movie;
    private int LikeNum;
    private List<Integer> joinedPromotions;

    public List<Integer> getJoinedPromotions() {
        return joinedPromotions;
    }

    public void setJoinedPromotions(List<Integer> joinedPromotions) {
        this.joinedPromotions = joinedPromotions;
    }

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
}
