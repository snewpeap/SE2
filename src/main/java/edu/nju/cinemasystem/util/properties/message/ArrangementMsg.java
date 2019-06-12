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
}
