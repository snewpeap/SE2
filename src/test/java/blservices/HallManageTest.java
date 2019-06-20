package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HallManageTest {

    @Autowired
    HallManage hallManage;

    @Test
    @Transactional
    public void testInputHallInfo1(){
        HallForm hallForm = new HallForm();
        hallForm.setName("影厅1");
        hallForm.setColumn(5);
        hallForm.setRow(3);
        hallForm.setSize("大");
        hallForm.setIs3d(true);
        hallForm.setIsImax(true);
        Response response = hallManage.inputHallInfo(hallForm);
        assertTrue(response.isSuccess());
    }

    @Test
    @Transactional
    public void testInputHallInfo2(){
        HallForm hallForm = new HallForm();
        hallForm.setName("八八八八");
        hallForm.setColumn(15);
        hallForm.setRow(8);
        hallForm.setSize("小");
        hallForm.setIs3d(false);
        hallForm.setIsImax(false);
        Response response = hallManage.inputHallInfo(hallForm);
        assertTrue(response.isSuccess());
    }


    @Test
    @Transactional
    public void testModifyHallInfo1(){
        HallForm hallForm = new HallForm();
        hallForm.setName("修改后");
        hallForm.setColumn(5);
        hallForm.setRow(6);
        hallForm.setSize("大");
        hallForm.setIs3d(true);
        hallForm.setIsImax(false);
        Response response = hallManage.modifyHallInfo(hallForm,1);
        System.out.println(response.getMessage());
        assertFalse(response.isSuccess());
    }

    @Test
    @Transactional
    public void  testGetAllInfo1(){
        Response re1 = hallManage.getAllHallInfo();
        assertNotNull(re1.getContent());
    }
}
