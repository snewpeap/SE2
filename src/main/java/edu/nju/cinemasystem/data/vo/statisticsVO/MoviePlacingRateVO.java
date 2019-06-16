package edu.nju.cinemasystem.data.vo.statisticsVO;

import java.util.Date;

public class MoviePlacingRateVO {
    private Date date;
    private Integer movieId;
    private Double rate;
    private String movieName;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the movieId
     */
    public Integer getMovieId() {
        return movieId;
    }

    /**
     * @param movieId the movieId to set
     */
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    /**
     * @return the rate
     */
    public Double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(Double rate) {
        this.rate = rate;
    }

    /**
     * @return the movieName
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * @param movieName the movieName to set
     */
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public MoviePlacingRateVO(Date date, Integer movieId, Double rate, String movieName) {
        this.date = date;
        this.movieId = movieId;
        this.rate = rate;
        this.movieName = movieName;
    }
}
