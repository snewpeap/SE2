package edu.nju.cinemasystem.util.properties.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "arrangement-msg")
public class ArrangementMsg extends GlobalMsg {

    private String visibleToAudience;
    private String arrangementStart;
    private String isStillHaveArrangement;
    private String isAlreadyHaveArrangement;
    private String timeConflict;
    private String fareCannotBeNegative;
    private String movieUnReleased;
    private  String durationIsShort;

    public String getArrangementStart() {
        return arrangementStart;
    }

    public void setArrangementStart(String arrangementStart) {
        this.arrangementStart = arrangementStart;
    }

    public String getVisibleToAudience() {
        return visibleToAudience;
    }

    public void setVisibleToAudience(String visibleToAudience) {
        this.visibleToAudience = visibleToAudience;
    }

    public String getIsStillHaveArrangement() {
        return isStillHaveArrangement;
    }

    public void setIsStillHaveArrangement(String isStillHaveArrangement) {
        this.isStillHaveArrangement = isStillHaveArrangement;
    }

    public String getIsAlreadyHaveArrangement() {
        return isAlreadyHaveArrangement;
    }

    public void setIsAlreadyHaveArrangement(String isAlreadyHaveArrangement) {
        this.isAlreadyHaveArrangement = isAlreadyHaveArrangement;
    }

    public String getTimeConflict() {
        return timeConflict;
    }

    public void setTimeConflict(String timeConflict) {
        this.timeConflict = timeConflict;
    }

    public String getFareCannotBeNegative() {
        return fareCannotBeNegative;
    }

    public void setFareCannotBeNegative(String fareCannotBeNegative) {
        this.fareCannotBeNegative = fareCannotBeNegative;
    }

    public String getMovieUnReleased() {
        return movieUnReleased;
    }

    public void setMovieUnReleased(String movieUnReleased) {
        this.movieUnReleased = movieUnReleased;
    }

    public String getDurationIsShort() {
        return durationIsShort;
    }

    public void setDurationIsShort(String durationIsShort) {
        this.durationIsShort = durationIsShort;
    }
}
