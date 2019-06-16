package edu.nju.cinemasystem.web.controller.user;

import edu.nju.cinemasystem.blservices.user.StaffManagement;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.StaffForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/root")
public class StaffManagementController {
    @Autowired
    private StaffManagement staffManagement;

    @GetMapping("/getStaff")
    public Response getStaff(@RequestParam List<String> query){
        return staffManagement.getStaff(query);
    }

    @PostMapping("/addStaff")
    public Response addStaff(@RequestBody StaffForm staffForm){
        return staffManagement.addStaff(staffForm);
    }

    @PostMapping("/addManager")
    public Response addManager(@RequestBody StaffForm staffForm){
        return staffManagement.addManager(staffForm);
    }

    @PostMapping("/removeStaff")
    public Response removeStaff(@RequestParam int staffId){
        return staffManagement.removeStaff(staffId);
    }

    @PostMapping("/changeRole")
    public Response changeRole(@RequestBody StaffForm staffForm){
        return staffManagement.changeRole(staffForm);
    }
}
