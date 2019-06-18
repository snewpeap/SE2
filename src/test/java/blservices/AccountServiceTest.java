package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.user.AccountService;
import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.data.vo.form.UserForm;
import edu.nju.cinemasystem.dataservices.user.UserMapper;
import edu.nju.cinemasystem.util.properties.AlipayProperties;
import edu.nju.cinemasystem.util.properties.message.AccountMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMsg accountMsg;
    @Autowired
    private AlipayProperties alipayProperties;

    @Test(expected = RuntimeException.class)
    @Transactional
    public void testRegister1() {
        System.out.println(alipayProperties.getNotifyUrl());
        RegistryForm registryForm = generateRF("123","123","123");
        Response response = accountService.register(registryForm);
        assertTrue(response.isSuccess());
        assertEquals(accountMsg.getRegistrySuccess(), response.getMessage());
        UserVO userVO = (UserVO) response.getContent();
        System.out.println("userID = " + userVO.getID());
        assertEquals("123", userVO.getName());
        throw new RuntimeException();
    }

    @Test(expected = RuntimeException.class)
    @Transactional
    public void testRegisterFail_passwordNotConfirmed(){
        RegistryForm registryForm = generateRF("qwe","qwe","ewq");
        Response response = accountService.register(registryForm);
        UserVO userVO = (UserVO) response.getContent();
        assertFalse(response.isSuccess());
        assertEquals(accountMsg.getPasswordNotConfirmed(),response.getMessage());
        assertNull(userVO);
        throw new RuntimeException();
    }

    @Test(expected = RuntimeException.class)
    @Transactional
    public void testRegisterFail_accountExist(){
        User sim = userMapper.selectByName("audience");
        RegistryForm registryForm = generateRF(sim.getName(),"123","123");
        Response response = accountService.register(registryForm);
        UserVO userVO = (UserVO) response.getContent();
        assertFalse(response.isSuccess());
        assertEquals(accountMsg.getRegistryNameAlreadyExist(),response.getMessage());
        assertNull(userVO);
        throw new RuntimeException();
    }

    private RegistryForm generateRF(String name,String password,String confirm){
        RegistryForm registryForm = new RegistryForm();
        registryForm.setName(name);
        registryForm.setPassword(password);
        registryForm.setConfirmPassword(confirm);
        return registryForm;
    }

    private UserForm generateUF(String name,String password){
        UserForm userForm = new UserForm();
        userForm.setName(name);
        userForm.setPassword(password);
        return userForm;
    }
}
