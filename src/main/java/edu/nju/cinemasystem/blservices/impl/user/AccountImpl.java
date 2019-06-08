package edu.nju.cinemasystem.blservices.impl.user;

import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.data.vo.form.UserForm;
import edu.nju.cinemasystem.dataservices.user.UserMapper;
import edu.nju.cinemasystem.util.properties.message.AccountMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class AccountImpl implements Account {
    private UserMapper userMapper;
    private AccountMsg accountMsg;

    @Autowired
    public AccountImpl(UserMapper userMapper, AccountMsg accountMsg) {
        this.userMapper = userMapper;
        this.accountMsg = accountMsg;
    }

    @Override
    public Response register(@NotNull RegistryForm registryForm) {
        Response response = Response.success();
        if (userMapper.selectByName(registryForm.getName()) != null) {
            response = Response.fail();
            response.setMessage(accountMsg.getRegistryNameAlreadyExist());
            return response;
        }
        if (registryForm.getPassword().equals(registryForm.getConfirmPassword())) {
            response = Response.fail();
            response.setMessage(accountMsg.getPasswordNotConfirmed());
            return response;
        }
        User user = new User();
        user.setName(registryForm.getName());
        user.setPassword(registryForm.getPassword());
        if (userMapper.insert(user) == 0) {
            response = Response.fail();
            response.setStatusCode(500);
            response.setMessage(accountMsg.getRegistryFailed());
        } else {
            user = userMapper.selectByName(registryForm.getName());
            response.setMessage(accountMsg.getRegistrySuccess());
            response.setContent(UserVO.assembleUserVO(user));
        }
        return response;
    }

    @Override
    public Response login(@NotNull UserForm userForm) {
        Response response = Response.fail();
        User targetUser = userMapper.selectByName(userForm.getName());
        if (targetUser == null) {
            response.setMessage(accountMsg.getAccountNotExist());
        } else if (!targetUser.getPassword().equals(userForm.getPassword())) {
            response.setMessage(accountMsg.getWrongPassword());
        } else {
            UserVO user = UserVO.assembleUserVO(targetUser);
            response = Response.success();
            response.setMessage(accountMsg.getLoginSuccess());
            response.setContent(user);
        }
        return response;
    }

    @Override
    public User getUserByID(@NotNull int ID) {
        return userMapper.selectByPrimaryKey(ID);
    }
}
