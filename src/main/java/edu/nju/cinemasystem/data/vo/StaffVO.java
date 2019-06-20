package edu.nju.cinemasystem.data.vo;

/**
 * 员工信息 继承自 UserVO 包括：
 * role 角色 staff:普通员工 manager:经理 root:管理员
 */
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
