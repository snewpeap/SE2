package edu.nju.cinemasystem.blservices.impl.user;

import edu.nju.cinemasystem.blservices.user.StaffManagement;
import edu.nju.cinemasystem.data.po.Manager;
import edu.nju.cinemasystem.data.po.Staff;
import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.po.UserHasRoleKey;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.StaffVO;
import edu.nju.cinemasystem.data.vo.form.StaffForm;
import edu.nju.cinemasystem.dataservices.user.RoleMapper;
import edu.nju.cinemasystem.dataservices.user.UserHasRoleMapper;
import edu.nju.cinemasystem.dataservices.user.UserMapper;
import edu.nju.cinemasystem.util.properties.RoleProperty;
import edu.nju.cinemasystem.util.properties.message.StaffMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffManagementImpl implements StaffManagement {
    private final StaffMsg staffMsg;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserHasRoleMapper userHasRoleMapper;
    private final RoleProperty roleProperty;

    @Autowired
    public StaffManagementImpl
            (StaffMsg staffMsg,
             UserMapper userMapper,
             RoleMapper roleMapper,
             UserHasRoleMapper userHasRoleMapper,
             RoleProperty roleProperty) {
        this.staffMsg = staffMsg;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userHasRoleMapper = userHasRoleMapper;
        this.roleProperty = roleProperty;
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
            if (roleProperty.getStaff().equals(queryString)) {
                userMapper.selectAllStaff().forEach(
                        staff -> staffList.add(assembleStaffVO(staff))//TODO 6/6写到这
                );
            } else if (roleProperty.getManager().equals(queryString)) {
                userMapper.selectAllManager().forEach(
                        manager -> staffList.add(assembleManagerVO(manager))
                );
            } else {
                response = Response.fail();
                response.setMessage(staffMsg.getWrongParam() + ':' + queryString);
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
        if (userMapper.selectStaffByName(staffForm.getName()) != null) {
            response = Response.fail();
            response.setMessage(staffMsg.getStaffAlreadyExist() + ':' + staffForm.getName());
            return response;
        }
        User staffAccount = assembleStaffAccount(staffForm);
        if (userMapper.insert(staffAccount) == 0) {
            response = Response.fail();
            response.setMessage(staffMsg.getRegistryFailed());
        } else {
            staffAccount = userMapper.selectStaffByName(staffAccount.getName());
            UserHasRoleKey uhr = new UserHasRoleKey();
            uhr.setUserId(staffAccount.getId());
            uhr.setRoleId(roleMapper.selectRoleIDByName(roleProperty.getStaff()));
            if (userHasRoleMapper.insert(uhr) == 0) {
                response = Response.fail();
                response.setMessage(staffMsg.getAddFailed());
            } else {
                response.setMessage(staffMsg.getAddSuccess());
                response.setContent(assembleStaffVO((Staff) staffAccount));
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
        if (userMapper.selectManagerByName(staffForm.getName()) != null) {
            response = Response.fail();
            response.setMessage(staffMsg.getManagerAlreadyExist() + ':' + staffForm.getName());
            return response;
        }
        User managerAccount = assembleStaffAccount(staffForm);
        //TODO
        if (userMapper.insert(managerAccount) == 0) {
            response = Response.fail();
            response.setMessage(staffMsg.getRegistryFailed());
        } else {
            UserHasRoleKey uhr = new UserHasRoleKey();
            uhr.setUserId(managerAccount.getId());
            uhr.setRoleId(roleMapper.selectRoleIDByName(roleProperty.getManager()));
            if (userHasRoleMapper.insert(uhr) == 0) {
                response = Response.fail();
                response.setMessage(staffMsg.getAddFailed());
            } else {
                response.setMessage(staffMsg.getAddSuccess());
                response.setContent(assembleManagerVO((Manager) managerAccount));
            }
        }
        return response;
    }

    @Override
    public Response removeStaff(int staffID) {
        Response response = Response.success();
        if (userHasRoleMapper.selectByUserID(staffID) == null) {
            response = Response.fail();
            response.setMessage(staffMsg.getStaffNotExist());
            return response;
        }
        if (userHasRoleMapper.deleteByUserID(staffID) == 0) {
            response = Response.fail();
            response.setMessage(staffMsg.getOperationFailed());
        }
        return response;
    }

    @Override
    public Response changeRole(StaffForm staffForm) {
        //TODO
        return null;
    }

    private User assembleStaffAccount(StaffForm staffForm) {
        User staff = new Staff();
        staff.setName(staffForm.getName());
        staff.setPassword(staffForm.getPassword());
        return staff;
    }

    private StaffVO assembleStaffVO(Staff staff) {
        return new StaffVO(
                staff.getId(),
                staff.getName(),
                roleProperty.getStaff()
        );
    }

    private StaffVO assembleManagerVO(Manager manager) {
        return new StaffVO(
                manager.getId(),
                manager.getName(),
                manager.isRootManager() ? roleProperty.getRoot() : roleProperty.getManager()
        );
    }
}
