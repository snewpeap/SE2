package edu.nju.cinemasystem.blservices.impl.user;

import edu.nju.cinemasystem.blservices.user.StaffManagement;
import edu.nju.cinemasystem.data.po.*;
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
import org.springframework.transaction.annotation.Transactional;

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
    public StaffManagementImpl(
            StaffMsg staffMsg,
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
                        staff -> staffList.add(assembleStaffVO(staff))
                );
            } else if (roleProperty.getManager().equals(queryString)) {
                userMapper.selectAllManager().forEach(
                        manager -> {
                            if (!manager.isRootManager()) {
                                staffList.add(assembleManagerVO(manager));
                            }
                        }
                );
            } else {
                return Response.fail(staffMsg.getWrongParam() + ':' + queryString);
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
    @Transactional
    public Response addStaff(StaffForm staffForm) {
        Response response = Response.success();
        if (userMapper.selectStaffByName(staffForm.getName()) != null) {
            return Response.fail(staffMsg.getStaffAlreadyExist() + ':' + staffForm.getName());
        }
        User staffAccount = assembleStaffAccount(staffForm);
        if (userMapper.insert(staffAccount) == 0) {
            response = Response.fail(staffMsg.getRegistryFailed());
        } else {
            UserHasRoleKey uhr = new UserHasRoleKey();
            uhr.setUserId(staffAccount.getId());
            uhr.setRoleId(roleMapper.selectRoleByName(roleProperty.getStaff()).getId());
            if (userHasRoleMapper.insert(uhr) == 0) {
                response = Response.fail(staffMsg.getAddFailed());
            } else {
                response.setMessage(staffMsg.getAddSuccess());
                response.setContent(new StaffVO(
                        staffAccount.getId(),
                        staffAccount.getName()
                ));
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
    @Transactional
    public Response addManager(StaffForm staffForm) {
        Response response = Response.success();
        if (userMapper.selectManagerByName(staffForm.getName()) != null) {
            return Response.fail(staffMsg.getManagerAlreadyExist() + ':' + staffForm.getName());
        }
        User managerAccount = assembleStaffAccount(staffForm);
        if (userMapper.insert(managerAccount) == 0) {
            response = Response.fail(staffMsg.getRegistryFailed());
        } else {
            UserHasRoleKey uhr = new UserHasRoleKey();
            uhr.setUserId(managerAccount.getId());
            uhr.setRoleId(roleMapper.selectRoleByName(roleProperty.getManager()).getId());
            if (userHasRoleMapper.insert(uhr) == 0) {
                response = Response.fail(staffMsg.getAddFailed());
            } else {
                response.setMessage(staffMsg.getAddSuccess());
                response.setContent(new StaffVO(
                        managerAccount.getId(),
                        managerAccount.getName(),
                        roleProperty.getManager()
                ));
            }
        }
        return response;
    }

    @Override
    public Response removeStaff(int staffID) {
        if (userHasRoleMapper.selectByUserID(staffID) == null) {
            return Response.fail(staffMsg.getStaffNotExist());
        }
        if (userHasRoleMapper.deleteByUserID(staffID) == 0) {
            return Response.fail(staffMsg.getOperationFailed());
        } else {
            return Response.success();
        }
    }

    @Override
    public Response changeRole(StaffForm staffForm) {
        String roleName = staffForm.getRole();
        if (roleName.equals(roleProperty.getAudience())) {
            return Response.fail(staffMsg.getWrongParam() + " : 不能将员工变成观众");
        } else if (roleName.equals(roleProperty.getRoot())) {
            return Response.fail(staffMsg.getWrongParam() + " : 铜锣湾只能有一个浩南");
        }
        Role role = roleMapper.selectRoleByName(staffForm.getRole());
        if (role == null) {
            return Response.fail(staffMsg.getWrongParam() + " : 角色不存在");
        }
        UserHasRoleKey uhr = userHasRoleMapper.selectByUserID(staffForm.getId());
        if (uhr == null) {
            return Response.fail(staffMsg.getOperationFailed() + " : 数据库用户角色缺少记录");
        }
        uhr.setRoleId(role.getId());
        userHasRoleMapper.updateByUserID(uhr);
        Response response = Response.success(staffMsg.getOperationSuccess());
        response.setContent(
                roleName.equals(roleProperty.getStaff()) ?
                        userMapper.selectStaffByName(staffForm.getName()) : userMapper.selectManagerByName(staffForm.getName())
        );
        return response;
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
