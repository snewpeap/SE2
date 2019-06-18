package edu.nju.cinemasystem.util.properties.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(value = "globalMsg")
@ConfigurationProperties(prefix = "global-msg")
public class GlobalMsg {
    private String WrongParam;
    private String operationSuccess;
    private String operationFailed;

    public String getWrongParam() {
        return WrongParam;
    }

    public void setWrongParam(String wrongParam) {
        WrongParam = wrongParam;
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
