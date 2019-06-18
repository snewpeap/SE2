package blservices;


import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.sale.promotion.PromotionService;
import edu.nju.cinemasystem.data.vo.form.PromotionForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PromotionServiceTest {

    @Autowired
    PromotionService promotionService;

    @Test
    @Transactional
    public void publishTest1(){
        PromotionForm promotionForm = new PromotionForm();
        promotionForm.setName("优惠1");
        String start = "2019-6-18";
        String end = "2019-6-24";
        Date[] dates = conversionTime(start,end);
        promotionForm.setStartTime(dates[0]);
        promotionForm.setEndTime(dates[1]);
        promotionForm.setSpecifyMovies(false);
        promotionForm.setTargetAmount((float)10);
        promotionForm.setCouponAmount((float)20);
        promotionForm.setCouponExpiration(10);
        promotionForm.setDescription("农夫山泉有点甜");
        assertTrue(promotionService.publishPromotion(promotionForm).isSuccess());
    }

    @Test
    @Transactional
    public void publishTest2(){
        PromotionForm promotionForm = new PromotionForm();
        promotionForm.setName("优惠2");
        String start = "2019-6-19";
        String end = "2019-6-23";
        Date[] dates = conversionTime(start,end);
        promotionForm.setStartTime(dates[0]);
        promotionForm.setEndTime(dates[1]);
        promotionForm.setSpecifyMovies(true);
        List<Integer> movieIDs = new ArrayList<>();
        movieIDs.add(1);
        promotionForm.setMovieIDs(movieIDs);
        promotionForm.setTargetAmount((float)12);
        promotionForm.setCouponAmount((float)21);
        promotionForm.setCouponExpiration(8);
        promotionForm.setDescription("大家好才是真的好");
        assertTrue(promotionService.publishPromotion(promotionForm).isSuccess());
    }

    @Test
    @Transactional
    public void publishTestNull(){
        PromotionForm promotionForm = new PromotionForm();
        promotionForm.setName("优惠3");
        String start = "2019-6-19";
        String end = "2019-6-23";
        Date[] dates = conversionTime(start,end);
        promotionForm.setStartTime(dates[0]);
        promotionForm.setEndTime(dates[1]);
        promotionForm.setSpecifyMovies(true);
        promotionForm.setCouponExpiration(8);
        promotionForm.setDescription("大家好才是真的好");
        assertFalse(promotionService.publishPromotion(promotionForm).isSuccess());
    }



    private Date[] conversionTime(String string1, String string2){
        Date[] dates = new Date[3];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dates[0] = simpleDateFormat.parse(string1);
            dates[1] = simpleDateFormat.parse(string2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

}
