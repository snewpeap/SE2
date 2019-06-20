package dataservices;


import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.data.po.Seat;
import edu.nju.cinemasystem.dataservices.cinema.hall.SeatMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SeatMapperTest {

    @Autowired
    SeatMapper seatMapper;


    @Test
    @Transactional
    public void selectByIDTest(){
        assertNotNull(seatMapper.selectByPrimaryKey(1));
    }

    @Test
    @Transactional
    public void updateSelectiveTestBySeat(){
        Seat seat = Seat.assembleSeatPO(2,2,1);
        seat.setId(1);
        assertTrue(seatMapper.updateByPrimaryKeySelective(seat)!=0);
    }

    @Test
    @Transactional
    public void selectByHallIDTest(){
        assertNotNull(seatMapper.selectByHallID(1));
    }

}
