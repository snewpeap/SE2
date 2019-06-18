package dataservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.data.po.Arrangement;
import edu.nju.cinemasystem.dataservices.cinema.arrangement.ArrangementMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ArrangementServiceMapperTest {

    @Autowired
    ArrangementMapper arrangementMapper;

    public void insertSelectiveTest1(){
        try {
        Arrangement arrangement = new Arrangement();
        arrangement.setHallId(1);
        arrangement.setFare((float)11);
        arrangement.setMovieId(1);
        arrangement.setStartTime(conversionTime("2019-07-11 12:21"));
        arrangement.setEndTime(conversionTime("2019-07-11 "));
        }catch (ParseException e){
            e.printStackTrace();
        }
    }



    private Date conversionTime(String string) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.parse(string);
    }
}
