package edu.nju.cinemasystem.util.properties.message;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "staff-msg")
public class StaffMsg extends AccountMsg {
    private String staffNotExist;
    private String managerNotExist;
    private String staffAlreadyExist;
    private String managerAlreadyExist;
    private String addFailed;
    private String addSuccess;

    public String getAddFailed() {
        return addFailed;
    }

    public void setAddFailed(String addFailed) {
        this.addFailed = addFailed;
    }

    public String getStaffNotExist() {
        return staffNotExist;
    }

    public void setStaffNotExist(String staffNotExist) {
        this.staffNotExist = staffNotExist;
    }

    public String getManagerNotExist() {
        return managerNotExist;
    }

    public void setManagerNotExist(String managerNotExist) {
        this.managerNotExist = managerNotExist;
    }

    public String getStaffAlreadyExist() {
        return staffAlreadyExist;
    }

    public void setStaffAlreadyExist(String staffAlreadyExist) {
        this.staffAlreadyExist = staffAlreadyExist;
    }

    public String getManagerAlreadyExist() {
        return managerAlreadyExist;
    }

    public void setManagerAlreadyExist(String managerAlreadyExist) {
        this.managerAlreadyExist = managerAlreadyExist;
    }

    public String getAddSuccess() {
        return addSuccess;
    }

    public void setAddSuccess(String addSuccess) {
        this.addSuccess = addSuccess;
    }
}
