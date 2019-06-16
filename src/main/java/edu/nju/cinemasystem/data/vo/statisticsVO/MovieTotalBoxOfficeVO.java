package edu.nju.cinemasystem.data.vo.statisticsVO;

public class MovieTotalBoxOfficeVO {
    private Integer movieId;
    /**
     * 票房(单位：元)
     */
    private float boxOffice;
    private String name;

    public MovieTotalBoxOfficeVO(Integer movieId, float boxOffice, String name) {
        this.movieId = movieId;
        this.boxOffice = boxOffice;
        this.name = name;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public float getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(float boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
