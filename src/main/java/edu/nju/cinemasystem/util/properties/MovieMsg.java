package edu.nju.cinemasystem.util.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "movie-msg")
public class MovieMsg {
    private String notExist;
    private String hasArrangement;
    private String operationSuccess;
    private String operationFailed;

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

    public String getOperationSuccess() {
        return operationSuccess;
    }

    public void setOperationSuccess(String operationSuccess) {
        this.operationSuccess = operationSuccess;
    }

    public String getOperationFailed() {
        return operationFailed;
    }

    public void setOperationFailed(String operationFailed) {
        this.operationFailed = operationFailed;
    }
}
