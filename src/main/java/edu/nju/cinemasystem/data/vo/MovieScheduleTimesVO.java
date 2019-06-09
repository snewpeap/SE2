package edu.nju.cinemasystem.data.vo;

public class MovieScheduleTimesVO {

    private Integer movieId;
    /**
     * 排片次数
     */
    private Integer times;
    private String name;

    public MovieScheduleTimesVO(Integer movieId, Integer times, String name) {
        this.movieId = movieId;
        this.times = times;
        this.name = name;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
