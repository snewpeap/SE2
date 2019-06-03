package blservices;

import static org.junit.Assert.*;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.user.Account;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.UserForm;
import edu.nju.cinemasystem.data.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountTest {

    @Autowired
    private Account account;

    @Test
    public void testLogin1(){
        UserForm userForm = new UserForm();
        userForm.setName("test");
        userForm.setPassword("test");
        Response response = account.login(userForm);
        UserVO userVO = (UserVO) response.getContent();
        assertTrue(response.isSuccess());
        assertEquals("登录成功",response.getMessage());
        assertEquals("test",userVO.getName());
        assertEquals(1,userVO.getID());
    }

}
