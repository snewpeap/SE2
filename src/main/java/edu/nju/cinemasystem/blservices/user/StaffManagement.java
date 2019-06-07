package edu.nju.cinemasystem.blservices.user;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.StaffForm;

import java.util.List;

public interface StaffManagement {

    /**
     * 根据一个查询条件来获得员工列表，query可以含有"staff"或"manager"
     *
     * @param query 查询条件
     * @return 含有员工列表的vo.Response
     */
    Response getStaff(List<String> query);

    /**
     * 添加员工，该业务方法只限于添加普通员工，普通管理员和root管理员可以添加
     * 建议Controller编写者将之与添加管理员的url分开，这样也方便进行权限校验
     *
     * @param staffForm 普通员工账号信息
     * @return 含有员工信息的vo.Response
     */
    Response addStaff(StaffForm staffForm);

    /**
     * 添加管理员，该业务方法只限于添加普通管理员，root管理员可以执行添加操作
     * 建议Controller编写者将之与添加员工的url分开，这样也方便进行权限校验
     * @param staffForm 普通管理员的账号信息
     * @return 含有管理员信息的vo.Response
     */
    Response addManager(StaffForm staffForm);

    Response removeStaff(int staffID);

    Response changeRole(StaffForm staffForm);
}
