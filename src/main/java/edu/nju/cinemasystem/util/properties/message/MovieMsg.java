package edu.nju.cinemasystem.util.properties.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "movie-msg")
public class MovieMsg extends GlobalMsg {
    private String notExist;
    private String hasArrangement;

    public String getHasArrangement() {
        return hasArrangement;
    }

    public void setHasArrangement(String hasArrangement) {
        this.hasArrangement = hasArrangement;
    }

    public String getNotExist() {
        return notExist;
    }

    public void setNotExist(String notExist) {
        this.notExist = notExist;
    }

}
