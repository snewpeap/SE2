package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementService;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.ArrangementForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ArrangementServiceManageTest {

    @Autowired
    ArrangementManage arrangementManage;

    @Test
    @Transactional
    public void addArrangementTest1(){
        ArrangementForm arrangementForm = new ArrangementForm();
        arrangementForm.setHallId(1);
        arrangementForm.setFare((float)12.0);
        arrangementForm.setMovieId(1);
        String startTimeString = "2019-6-14 13:12";
        String endTimeString = "2019-6-14 15:50";
        Date[] dates = conversionTime(startTimeString,endTimeString,startTimeString);
        arrangementForm.setStartTime(dates[0]);
        arrangementForm.setEndTime(dates[1]);
        arrangementForm.setVisibleDate(dates[2]);
        Response response = arrangementManage.addArrangement(arrangementForm);
        System.out.println(response.getMessage());
        assertFalse(response.isSuccess());

    }

    @Test
    @Transactional
    public void addArrangementTest2(){
        ArrangementForm arrangementForm = new ArrangementForm();
        arrangementForm.setHallId(1);
        arrangementForm.setFare((float)24);
        arrangementForm.setMovieId(1);
        String startTimeString = "2019-6-13 13:12";
        String endTimeString = "2019-6-13 15:50";
        Date[] dates = conversionTime(startTimeString,endTimeString,startTimeString);
        arrangementForm.setStartTime(dates[0]);
        arrangementForm.setEndTime(dates[1]);
        arrangementForm.setVisibleDate(dates[2]);
        Response response = arrangementManage.addArrangement(arrangementForm);
        System.out.println(response.getMessage());
        assertFalse(response.isSuccess());

    }

    @Test
    @Transactional
    public void addArrangementTest_TimeConflict(){
        ArrangementForm arrangementForm = new ArrangementForm();
        arrangementForm.setHallId(1);
        arrangementForm.setFare((float)24);
        arrangementForm.setMovieId(1);
        String startTimeString = "2019-6-14 13:12";
        String endTimeString = "2019-6-14 13:59";
        Date[] dates = conversionTime(startTimeString,endTimeString,startTimeString);
        arrangementForm.setStartTime(dates[0]);
        arrangementForm.setEndTime(dates[1]);
        arrangementForm.setVisibleDate(dates[2]);
        Response response = arrangementManage.addArrangement(arrangementForm);
        System.out.println(response.getMessage());
        assertFalse(response.isSuccess());
    }

    @Test
    @Transactional
    public void modifyArrangementTest1(){
        ArrangementForm arrangementForm = new ArrangementForm();
        int id = 2;
        arrangementForm.setHallId(1);
        arrangementForm.setFare((float)35);
        arrangementForm.setMovieId(1);
        String startTimeString = "2019-6-14 13:10";
        String endTimeString = "2019-6-14 18:50";
        Date[] dates = conversionTime(startTimeString,endTimeString,startTimeString);
        arrangementForm.setStartTime(dates[0]);
        arrangementForm.setEndTime(dates[1]);
        arrangementForm.setVisibleDate(dates[2]);
        Response response = arrangementManage.modifyArrangement(arrangementForm,id);
        System.out.println(response.getMessage());
        assertFalse(response.isSuccess());
    }

    private Date[] conversionTime(String string1, String string2, String string3){
        Date[] dates = new Date[3];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            dates[0] = simpleDateFormat.parse(string1);
            dates[1] = simpleDateFormat.parse(string2);
            dates[2] = simpleDateFormat.parse(string3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

}
