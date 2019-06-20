package edu.nju.cinemasystem.data.vo.statisticsVO;

import edu.nju.cinemasystem.data.vo.AudienceMovieVO;
import edu.nju.cinemasystem.data.vo.BaseMovieVO;

/**
 * 电影受欢迎程度信息 包括：
 * movie 是一个BaseMovieVO
 * boxOffice 电影票房
 */
public class MoviePopularityVO {
    private BaseMovieVO movie;
    private float boxOffice;

    /**
     * @return the movie
     */
    public BaseMovieVO getMovie() {
        return movie;
    }

    /**
     * @param movie the movie to set
     */
    public void setMovie(AudienceMovieVO movie) {
        this.movie = movie;
    }

    /**
     * @return the boxOffice
     */
    public float getBoxOffice() {
        return boxOffice;
    }

    /**
     * @param boxOffice the boxOffice to set
     */
    public void setBoxOffice(float boxOffice) {
        this.boxOffice = boxOffice;
    }

    public MoviePopularityVO(BaseMovieVO movie, float boxOffice) {
        this.movie = movie;
        this.boxOffice = boxOffice;
    }
}
