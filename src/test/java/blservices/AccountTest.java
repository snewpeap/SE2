package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserVO;
import edu.nju.cinemasystem.data.vo.form.RegistryForm;
import edu.nju.cinemasystem.data.vo.form.UserForm;
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
public class AccountTest {

    @Autowired
    private Account account;
    @Autowired
    private AccountMsg accountMsg;

    @Test(expected = RuntimeException.class)
    @Transactional
    public void testRegister1() {
        RegistryForm registryForm = new RegistryForm();
        registryForm.setName("qwe");
        registryForm.setPassword("qwe");
        registryForm.setConfirmPassword("qwe");
        Response response = account.register(registryForm);
        assertTrue(response.isSuccess());
        assertEquals(accountMsg.getRegistrySuccess(), response.getMessage());
        UserVO userVO = (UserVO) response.getContent();
        System.out.println("userID = " + userVO.getID());
        assertEquals("qwe", userVO.getName());
        throw new RuntimeException();
    }

    @Test
    public void testLogin1() {
        UserForm userForm = new UserForm();
        userForm.setName("audience");
        userForm.setPassword("audience");
        Response response = account.login(userForm);
        UserVO userVO = (UserVO) response.getContent();
        assertTrue(response.isSuccess());
        assertEquals(accountMsg.getLoginSuccess(), response.getMessage());
        assertEquals("audience", userVO.getName());
        assertEquals(4, userVO.getID());
    }

    @Test
    public void testLoginFail1() {
        UserForm userForm = new UserForm();
        userForm.setName("null");
        userForm.setPassword("null");
        Response response = account.login(userForm);
        UserVO userVO = (UserVO) response.getContent();
        assertFalse(response.isSuccess());
        assertEquals(accountMsg.getAccountNotExist(), response.getMessage());
        assertNull(userVO);
    }
}
