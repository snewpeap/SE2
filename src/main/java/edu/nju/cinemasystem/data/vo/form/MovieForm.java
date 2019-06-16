package edu.nju.cinemasystem.data.vo.form;

import edu.nju.cinemasystem.data.vo.BaseMovieVO;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class MovieForm extends BaseMovieVO {
    private Integer id;

    @NotBlank
    private String name;

    @URL
    private String poster;

    @NotBlank
    private String director;

    @NotBlank
    private String screenWriter;

    @NotBlank
    private String starring;

    @NotBlank
    private String type;

    @NotNull
    private String country;

    @NotBlank
    private String language;

    @NotNull
    @Positive
    private Integer duration;

    @NotNull
    @Future
    private Date startDate;

    @NotBlank
    private String description;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPoster() {
        return poster;
    }

    @Override
    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String getDirector() {
        return director;
    }

    @Override
    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String getScreenWriter() {
        return screenWriter;
    }

    @Override
    public void setScreenWriter(String screenWriter) {
        this.screenWriter = screenWriter;
    }

    @Override
    public String getStarring() {
        return starring;
    }

    @Override
    public void setStarring(String starring) {
        this.starring = starring;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public Integer getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
