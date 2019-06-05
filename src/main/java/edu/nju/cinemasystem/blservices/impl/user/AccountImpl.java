package edu.nju.cinemasystem.blservices.impl.user;

import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Form.RegistryForm;
import edu.nju.cinemasystem.data.vo.Form.UserForm;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.dataservices.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class AccountImpl implements Account {
    private UserMapper userMapper;

    @Autowired
    public AccountImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Response register(@NotNull RegistryForm registryForm) {
        Response response = Response.success();
        if (userMapper.selectByName(registryForm.getName()) != null) {
            response = Response.fail();
            response.setMessage("用户名已经被注册");
            return response;
        }
        if (registryForm.getPassword().equals(registryForm.getConfirmPassword())) {
            response = Response.fail();
            response.setMessage("两次输入的密码不同");
            return response;
        }
        User user = new User();
        user.setName(registryForm.getName());
        user.setPassword(registryForm.getPassword());
        int id = userMapper.insert(user);
        user = userMapper.selectByPrimaryKey(id);
        response.setMessage("注册成功");
        response.setContent(new UserVO(user.getId(),user.getName()));
        return response;
    }

    @Override
    public Response login(@NotNull UserForm userForm) {
        Response response = Response.fail();
        User targetUser = userMapper.selectByName(userForm.getName());
        if (targetUser == null) {
            response.setMessage("用户名不存在");
            return response;
        }
        if (!targetUser.getPassword().equals(userForm.getPassword())) {
            response.setMessage("密码错误");
            return response;
        }
        UserVO user = new UserVO(targetUser.getId(),targetUser.getName());
        response = Response.success();
        response.setMessage("登录成功");
        response.setContent(user);
        return response;
    }

    public User getUserByID(@NotNull int ID) {
        return userMapper.selectByPrimaryKey(ID);
    }
}
