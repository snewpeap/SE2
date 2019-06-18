package edu.nju.cinemasystem.blservices.user;

import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;

import javax.validation.constraints.NotNull;

public interface AccountService {
    /**
     * 注册，检查是否有重名和密码是否一致，成功注册
     * @param registryForm 注册表单
     * @return 注册成功的用户VO
     */
    Response register(RegistryForm registryForm);

    User getUserByID(@NotNull int ID);

    UserVO getUserVOByName(@NotNull String name);
}
