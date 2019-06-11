package edu.nju.cinemasystem.util.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "role")
public class RoleProperty {
    private String staff;
    private String manager;
    private String root;
    private String audience;

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public int getRoleLevel(String roleName){
        if (getRoot().equals(roleName)) {
            return 4;
        } else if (getManager().equals(roleName)) {
            return 3;
        } else if (getStaff().equals(roleName)) {
            return 2;
        }
        return 1;
    }

    public String getRole(int level){
        if (level >= 4){
            return getRoot();
        }else if (level == 3){
            return getManager();
        }else if (level == 2){
            return getStaff();
        }else {
            return getAudience();
        }
    }
}
