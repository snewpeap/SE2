package edu.nju.cinemasystem.util.properties.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "account-msg")
public class AccountMsg extends GlobalMsg {
    private String registryNameAlreadyExist;
    private String passwordNotConfirmed;
    private String accountNotExist;
    private String wrongPassword;
    private String loginSuccess;
    private String registrySuccess;
    private String registryFailed;

    public String getRegistryFailed() {
        return registryFailed;
    }

    public void setRegistryFailed(String registryFailed) {
        this.registryFailed = registryFailed;
    }

    public String getRegistryNameAlreadyExist() {
        return registryNameAlreadyExist;
    }

    public void setRegistryNameAlreadyExist(String registryNameAlreadyExist) {
        this.registryNameAlreadyExist = registryNameAlreadyExist;
    }

    public String getPasswordNotConfirmed() {
        return passwordNotConfirmed;
    }

    public void setPasswordNotConfirmed(String passwordNotConfirmed) {
        this.passwordNotConfirmed = passwordNotConfirmed;
    }

    public String getAccountNotExist() {
        return accountNotExist;
    }

    public void setAccountNotExist(String accountNotExist) {
        this.accountNotExist = accountNotExist;
    }

    public String getWrongPassword() {
        return wrongPassword;
    }

    public void setWrongPassword(String wrongPassword) {
        this.wrongPassword = wrongPassword;
    }

    public String getLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(String loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    public String getRegistrySuccess() {
        return registrySuccess;
    }

    public void setRegistrySuccess(String registrySuccess) {
        this.registrySuccess = registrySuccess;
    }
}
