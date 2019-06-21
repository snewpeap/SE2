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

    /**
     * 获取员工，根据一个查询条件来获得员工列表，query可以含有"staff"或"manager"，不包括root账户
     * @param query 查询的角色名
     * @return 符合条件的员工
     */
    @GetMapping("/getStaff")
    public Response getStaff(@RequestParam List<String> query){
        return staffManagement.getStaff(query);
    }

    /**
     * 添加员工
     * @param staffForm 员工表单
     * @return 添加结果
     */
    @PostMapping("/addStaff")
    public Response addStaff(@RequestBody StaffForm staffForm){
        return staffManagement.addStaff(staffForm);
    }

    /**
     * 添加管理员（只限manager，不能是root）
     * @param staffForm 员工表单
     * @return 添加结果
     */
    @PostMapping("/addManager")
    public Response addManager(@RequestBody StaffForm staffForm){
        return staffManagement.addManager(staffForm);
    }

    /**
     * 移除员工
     * @param staffId 员工id
     * @return 移除的结果
     */
    @PostMapping("/removeStaff")
    public Response removeStaff(@RequestParam int staffId){
        return staffManagement.removeStaff(staffId);
    }

    /**
     * 改变员工的角色
     * @param staffForm 修改角色的员工表单
     * @return 修改的结果
     */
    @PostMapping("/changeRole")
    public Response changeRole(@RequestBody StaffForm staffForm){
        return staffManagement.changeRole(staffForm);
    }
}
