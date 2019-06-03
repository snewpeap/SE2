package edu.nju.cinemasystem.blservices.impl.user;

import edu.nju.cinemasystem.blservices.user.StaffManagement;
import edu.nju.cinemasystem.data.po.Manager;
import edu.nju.cinemasystem.data.po.Staff;
import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.StaffForm;
import edu.nju.cinemasystem.data.vo.StaffVO;
import edu.nju.cinemasystem.dataservices.ManagerMapper;
import edu.nju.cinemasystem.dataservices.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffManagementImpl implements StaffManagement {
    private StaffMapper staffMapper;
    private ManagerMapper managerMapper;
    private List<String> roles_limitation;

    @Autowired
    public StaffManagementImpl(StaffMapper staffMapper, ManagerMapper managerMapper) {
        this.staffMapper = staffMapper;
        this.managerMapper = managerMapper;
        roles_limitation = new ArrayList<>();
        roles_limitation.add("staff");
        roles_limitation.add("manager");
    }

    /**
     * 根据一个查询条件来获得员工列表，query可以含有"staff"或"manager"
     *
     * @param query 查询条件
     * @return 含有员工列表的vo.Response
     */
    @Override
    public Response getStaff(List<String> query) {
        Response response = Response.success();
        if (query == null) {
            return response;
        }
        List<StaffVO> staffList = new ArrayList<>();
        for (String queryString : query) {
            queryString = queryString.toLowerCase();
            if (!roles_limitation.contains(queryString)) {
                response = Response.fail();
                response.setMessage("查询参数错误:" + queryString);
                return response;
            }
            switch (queryString) {
                case "staff":
                    staffMapper.selectAll().forEach(
                            staff -> {
                                StaffVO staffVO = new StaffVO(
                                        staff.getId(),
                                        staff.getName(),
                                        "staff"
                                );
                                staffList.add(staffVO);
                            }
                    );
                    break;
                case "manager":
                    managerMapper.selectAll().forEach(
                            manager -> {
                                int id = manager.getId();
                                StaffVO staffVO = new StaffVO(id, manager.getName());
                                staffVO.setRole(
                                        managerMapper.selectManagerByPrimaryKey(id).isRootManager() ?
                                                "root" :
                                                "manager"
                                );
                                staffList.add(staffVO);
                            }
                    );
                    break;
            }
        }
        response.setContent(staffList);
        return response;
    }

    /**
     * 添加员工，该业务方法只限于添加普通员工，普通管理员和root管理员可以添加
     * 建议Controller编写者将之与添加管理员的url分开，这样也方便进行权限校验
     *
     * @param staffForm 普通员工账号信息
     * @return 含有员工信息的vo.Response
     */
    @Override
    public Response addStaff(StaffForm staffForm) {
        Response response = Response.success();
        if (staffMapper.selectByName(staffForm.getName()) != null) {
            response = Response.fail();
            response.setMessage("员工用户" + staffForm.getName() + "已存在");
            return response;
        }
        User staffAccount = new User();
        staffAccount.setName(staffForm.getName());
        staffAccount.setPassword(staffForm.getPassword());
        int id = staffMapper.insert(staffAccount);
        Staff staff = new Staff();
        staff.setUserId(id);
        staffMapper.insert(staff);
        response.setMessage("添加成功");
        response.setContent(new StaffVO(id, staffForm.getName(), staffForm.getRole()));
        return response;
    }

    /**
     * 添加管理员，该业务方法只限于添加普通管理员，root管理员可以执行添加操作
     * 建议Controller编写者将之与添加员工的url分开，这样也方便进行权限校验
     * @param staffForm 普通管理员的账号信息
     * @return 含有管理员信息的vo.Response
     */
    @Override
    public Response addManager(StaffForm staffForm) {
        Response response = Response.success();
        if (managerMapper.selectByName(staffForm.getName()) != null) {
            response = Response.fail();
            response.setMessage("管理员用户" + staffForm.getName() + "已存在");
            return response;
        }
        User managerAccount = new User();
        managerAccount.setName(staffForm.getName());
        managerAccount.setPassword(staffForm.getPassword());
        int id = managerMapper.insert(managerAccount);
        Manager manager = new Manager();
        manager.setUserId(id);
        manager.setRoot(false);
        managerMapper.insert(manager);
        response.setMessage("添加成功");
        response.setContent(new StaffVO(id, staffForm.getName(), staffForm.getRole()));
        return response;
    }

    @Override
    public Response removeStaff(int staffID) {
        Response response = Response.success();
        if (staffMapper.selectByPrimaryKey(staffID)==null){
            response = Response.fail();
            response.setMessage("员工不存在");
            return response;
        }
        staffMapper.deleteByPrimaryKey(staffID);
        return response;
    }

    @Override
    public Response removeManager(int staffID) {
        Response response = Response.fail();
        if (managerMapper.selectManagerByPrimaryKey(staffID)==null){
            response.setMessage("管理员不存在");
            return response;
        }
        managerMapper.deleteByPrimaryKey(staffID);
        response = Response.success();
        return response;
    }

    @Override
    public Response changeRole(StaffForm staffForm) {
        //TODO
        return null;
    }
}
