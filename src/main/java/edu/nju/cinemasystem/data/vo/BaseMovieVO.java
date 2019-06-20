package edu.nju.cinemasystem.data.vo;

import edu.nju.cinemasystem.data.po.Movie;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MovieVO 基类 包含：
 * id 序号
 * name 名字
 * poster 海报url
 * director 导演
 * screenWriter 编剧
 * starring 主演
 * type 电影类型
 * country 国家地区
 * language 语言
 * duration 长度
 * startDate 什么日期上映
 * description 简介
 *
 */
public abstract class BaseMovieVO {
    private Integer id;

    private String name;

    private String poster;

    private String director;

    private String screenWriter;

    private String starring;

    private String type;

    private String country;

    private String language;

    private Integer duration;

    private Date startDate;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScreenWriter() {
        return screenWriter;
    }

    public void setScreenWriter(String screenWriter) {
        this.screenWriter = screenWriter;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Movie assembleMoviePO(BaseMovieVO movieVO){
        Movie movie = new Movie();
        movie.setId(movieVO.getId());
        movie.setName(movieVO.getName());
        movie.setCountry(movieVO.getCountry());
        movie.setDescription(movieVO.getDescription());
        movie.setDirector(movieVO.getDirector());
        movie.setDuration(movieVO.getDuration());
        movie.setLanguage(movieVO.getLanguage());
        movie.setPoster(movieVO.getPoster());
        movie.setScreenWriter(movieVO.getScreenWriter());
        movie.setStarring(movieVO.getStarring());
        movie.setStartDate(movieVO.getStartDate());
        movie.setType(movieVO.getType());
        Date today = getNowDay();
        if(today.compareTo(movieVO.getStartDate()) >= 0){
            movie.setStatus((byte)1);
        }else {
            movie.setStatus((byte)0);
        }
        return movie;
    }


    public static void assembleMovieVO(Movie movie, BaseMovieVO movieVO) {
        movieVO.setId(movie.getId());
        movieVO.setCountry(movie.getCountry());
        movieVO.setDescription(movie.getDescription());
        movieVO.setDirector(movie.getDirector());
        movieVO.setDuration(movie.getDuration());
        movieVO.setLanguage(movie.getLanguage());
        movieVO.setName(movie.getName());
        movieVO.setPoster(movie.getPoster());
        movieVO.setScreenWriter(movie.getScreenWriter());
        movieVO.setStarring(movie.getStarring());
        movieVO.setStartDate(movie.getStartDate());
        movieVO.setType(movie.getType());
    }

    private static Date getNowDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(dateString,pos);
    }
}
