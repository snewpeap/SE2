package edu.nju.cinemasystem.blservices.user;

import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.data.vo.form.UserForm;

import javax.validation.constraints.NotNull;

public interface Account {
    /**
     * 注册，检查是否有重名和密码是否一致，成功注册
     * @param registryForm 注册表单
     * @return 注册成功的用户VO
     */
    Response register(RegistryForm registryForm);

    /**
     * 登录，检查用户的存在性和密码的正确性，成功登录
     * @param userForm 登录表单
     * @return 登录成功的用户VO
     */
    Response login(UserForm userForm);

    User getUserByID(@NotNull int ID);

    UserVO getUserVOByName(@NotNull String name);
}
