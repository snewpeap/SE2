package edu.nju.cinemasystem.blservices.user;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.StaffForm;

public interface StaffManagement {
    Response getStaff(String query);
    Response addStaff(StaffForm staffForm);
    Response removeStaff(int staffID);
    Response changeRole(StaffForm staffForm);
}
