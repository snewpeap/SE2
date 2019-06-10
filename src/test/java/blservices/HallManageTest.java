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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HallManageTest {

    @Autowired
    HallManage hallManage;

    @Test
    public void testInputHallInfo1(){
        HallForm hallForm = new HallForm();
        hallForm.setName("啊啊啊啊");
        hallForm.setColumn(30);
        hallForm.setRow(10);
        hallForm.setSize("大");
        hallForm.setIs3d(true);
        hallForm.setIsImax(true);
        Response response = hallManage.inputHallInfo(hallForm);
        assert response.isSuccess();
    }

    @Test
    public void testInputHallInfo2(){
        HallForm hallForm = new HallForm();
        hallForm.setName("八八八八");
        hallForm.setColumn(15);
        hallForm.setRow(8);
        hallForm.setSize("小");
        hallForm.setIs3d(false);
        hallForm.setIsImax(false);
        Response response = hallManage.inputHallInfo(hallForm);
        assert response.isSuccess();
    }

    @Test
    public void testModifyHallInfo1(){
        HallForm hallForm = new HallForm();
        hallForm.setName("eeee");
        hallForm.setColumn(30);
        hallForm.setRow(10);
        hallForm.setSize("大");
        hallForm.setIs3d(true);
        hallForm.setIsImax(true);
        Response response = hallManage.modifyHallInfo(hallForm,1);
        assert response.isSuccess();
    }

    @Test
    public void testModifyHallInfo2(){
        HallForm hallForm = new HallForm();
        hallForm.setName("八八八八");
        hallForm.setColumn(30);
        hallForm.setRow(10);
        hallForm.setSize("大");
        hallForm.setIs3d(true);
        hallForm.setIsImax(false);
        Response response = hallManage.modifyHallInfo(hallForm,2);
        assert response.isSuccess();
    }

    @Test
    public void  testGetAllInfo1(){
        Response re1 = hallManage.getAllHallInfo();

    }
}
