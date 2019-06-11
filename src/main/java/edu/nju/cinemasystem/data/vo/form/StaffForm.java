package edu.nju.cinemasystem.data.vo.form;

import javax.validation.constraints.NotBlank;

public class StaffForm extends UserForm {
    @NotBlank
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
