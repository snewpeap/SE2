package edu.nju.cinemasystem.data.po;

public class PromotionHasMovie {
    private Integer promotionId;

    private Integer movieId;

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public PromotionHasMovie(Integer promotionId, Integer movieId) {
        this.promotionId = promotionId;
        this.movieId = movieId;
    }
}