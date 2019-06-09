package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.user.StaffManagement;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.StaffVO;
import edu.nju.cinemasystem.dataservices.user.RoleMapper;
import edu.nju.cinemasystem.dataservices.user.UserHasRoleMapper;
import edu.nju.cinemasystem.dataservices.user.UserMapper;
import edu.nju.cinemasystem.util.properties.RoleProperty;
import edu.nju.cinemasystem.util.properties.message.StaffMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class StaffManageTest {

    @Autowired
    private StaffManagement staffManagement;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserHasRoleMapper hasRoleMapper;
    @Autowired
    private RoleProperty roleProperty;
    @Autowired
    private StaffMsg staffMsg;

    @Test
    public void testGetStaff_staff() {
        List<String> query = new ArrayList<>(1);
        query.add(roleProperty.getStaff());
        Response response = staffManagement.getStaff(query);
        List<StaffVO> staffs = (List<StaffVO>) response.getContent();
    }
}
