package edu.nju.cinemasystem.blservices.user;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.StaffForm;

import java.util.List;

public interface StaffManagement {
    Response getStaff(List<String> query);

    Response addStaff(StaffForm staffForm);

    Response addManager(StaffForm staffForm);

    Response removeStaff(int staffID);

    Response removeManager(int staffID);

    Response changeRole(StaffForm staffForm);
}
