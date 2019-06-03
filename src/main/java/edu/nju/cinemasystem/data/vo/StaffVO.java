package edu.nju.cinemasystem.data.vo;

public class StaffVO extends UserVO {
    private String role;

    public StaffVO(int ID, String name) {
        super(ID, name);
    }

    public StaffVO(int ID, String name, String role) {
        super(ID, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
