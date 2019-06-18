package edu.nju.cinemasystem.blservices.impl.user;

import edu.nju.cinemasystem.blservices.user.AccountService;
import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.dataservices.user.UserMapper;
import edu.nju.cinemasystem.util.properties.message.AccountMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class AccountImpl implements AccountService {
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
            return Response.fail(accountMsg.getRegistryNameAlreadyExist());
        }
        if (!registryForm.getPassword().equals(registryForm.getConfirmPassword())) {
            return Response.fail(accountMsg.getPasswordNotConfirmed());
        }
        User user = new User();
        user.setName(registryForm.getName());
        user.setPassword(registryForm.getPassword());
        if (userMapper.insert(user) == 0) {
            response = Response.fail(accountMsg.getRegistryFailed());
        } else {
            user = userMapper.selectByPrimaryKey(user.getId());
            response.setMessage(accountMsg.getRegistrySuccess());
            response.setContent(UserVO.assembleUserVO(user));
        }
        return response;
    }

    @Override
    public User getUserByID(@NotNull int ID) {
        return userMapper.selectByPrimaryKey(ID);
    }

    @Override
    public UserVO getUserVOByName(@NotNull String name) {
        return UserVO.assembleUserVO(userMapper.selectByName(name));
    }
}
