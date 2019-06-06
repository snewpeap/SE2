package edu.nju.cinemasystem.blservices.impl.user;

import edu.nju.cinemasystem.blservices.user.StaffManagement;
import edu.nju.cinemasystem.data.po.Manager;
import edu.nju.cinemasystem.data.po.Staff;
import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Form.StaffForm;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.StaffVO;
import edu.nju.cinemasystem.dataservices.user.ManagerMapper;
import edu.nju.cinemasystem.dataservices.user.StaffMapper;
import edu.nju.cinemasystem.util.properties.StaffMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffManagementImpl implements StaffManagement {
    private final StaffMsg staffMsg;
    private StaffMapper staffMapper;
    private ManagerMapper managerMapper;
    private List<String> roles_limitation;

    @Autowired
    public StaffManagementImpl(StaffMapper staffMapper, ManagerMapper managerMapper, StaffMsg staffMsg) {
        this.staffMapper = staffMapper;
        this.managerMapper = managerMapper;
        roles_limitation = new ArrayList<>();
        roles_limitation.add("staff");
        roles_limitation.add("manager");
        this.staffMsg = staffMsg;
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
                response.setMessage(staffMsg.getWrongParam() + ':' + queryString);
                return response;
            }
            switch (queryString) {
                case "staff":
                    staffMapper.selectAll().forEach(
                            staff -> staffList.add(assembleStaffVO(staff))
                    );
                    break;
                case "manager":
                    managerMapper.selectAll().forEach(
                            manager -> staffList.add(assembleManagerVO(
                                    manager,
                                    managerMapper.selectManagerByPrimaryKey(manager.getId()).isRootManager())
                            )
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
            response.setMessage(staffMsg.getStaffAlreadyExist() + ':' + staffForm.getName());
            return response;
        }
        User staffAccount = assembleStaffAccount(staffForm);
        if (staffMapper.insert(staffAccount) == 0) {
            response = Response.fail();
            response.setMessage(staffMsg.getRegistryFailed());
        } else {
            Staff staff = new Staff();
            staffAccount = staffMapper.selectByName(staffAccount.getName());
            staff.setUserId(staffAccount.getId());
            if (staffMapper.insert(staff) == 0) {
                response = Response.fail();
                response.setMessage(staffMsg.getAddFailed());
            } else {
                response.setMessage(staffMsg.getAddSuccess());
                response.setContent(assembleStaffVO(staffAccount));
            }
        }
        return response;
    }

    /**
     * 添加管理员，该业务方法只限于添加普通管理员，root管理员可以执行添加操作
     * 建议Controller编写者将之与添加员工的url分开，这样也方便进行权限校验
     *
     * @param staffForm 普通管理员的账号信息
     * @return 含有管理员信息的vo.Response
     */
    @Override
    public Response addManager(StaffForm staffForm) {
        Response response = Response.success();
        if (managerMapper.selectByName(staffForm.getName()) != null) {
            response = Response.fail();
            response.setMessage(staffMsg.getManagerAlreadyExist() + ':' + staffForm.getName());
            return response;
        }
        User managerAccount = assembleStaffAccount(staffForm);
        //TODO
        if (managerMapper.insert(managerAccount) == 0) {
            response = Response.fail();
            response.setMessage(staffMsg.getRegistryFailed());
        } else {
            Manager manager = new Manager();
            managerAccount = managerMapper.selectByName(managerAccount.getName());
            manager.setUserId(managerAccount.getId());
            manager.setRoot(false);
            if (managerMapper.insert(manager) == 0) {
                response = Response.fail();
                response.setMessage(staffMsg.getAddFailed());
            } else {
                response.setMessage(staffMsg.getAddSuccess());
                response.setContent(assembleManagerVO(managerAccount, false));
            }
        }
        return response;
    }

    @Override
    public Response removeStaff(int staffID) {
        Response response = Response.success();
        if (staffMapper.selectByPrimaryKey(staffID) == null) {
            response = Response.fail();
            response.setMessage(staffMsg.getStaffNotExist());
            return response;
        }
        if (staffMapper.deleteByPrimaryKey(staffID) == 0) {
            response = Response.fail();
            response.setMessage(staffMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response removeManager(int staffID) {
        Response response = Response.fail();
        if (managerMapper.selectManagerByPrimaryKey(staffID) == null) {
            response.setMessage(staffMsg.getManagerNotExist());
            return response;
        }
        if (managerMapper.deleteByPrimaryKey(staffID) == 0) {
            response.setMessage(staffMsg.getOperationFailed());
        } else {
            response = Response.success();
        }
        return response;
    }

    @Override
    public Response changeRole(StaffForm staffForm) {
        //TODO
        return null;
    }

    private User assembleStaffAccount(StaffForm staffForm) {
        User user = new User();
        user.setName(staffForm.getName());
        user.setPassword(staffForm.getPassword());
        return user;
    }

    private StaffVO assembleStaffVO(User staff) {
        return new StaffVO(
                staff.getId(),
                staff.getName(),
                "staff"
        );
    }

    private StaffVO assembleManagerVO(User manager, boolean isRoot) {
        return new StaffVO(
                manager.getId(),
                manager.getName(),
                isRoot ? "root" : "manager"
        );
    }
}
