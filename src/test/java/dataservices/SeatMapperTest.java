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
    public void deleteByIDTest1(){
        assertTrue(seatMapper.deleteByPrimaryKey(31)!=0);
    }

    @Test
    @Transactional
    public void insertTest1(){
        Seat seat = new Seat();
        seat.setColumn(1);
        seat.setRow(2);
        seat.setHallId(2);
        assertTrue(seatMapper.insert(seat)!=0);
    }

    @Test
    @Transactional
    public void insertSelectiveTest1(){
        Seat seat = new Seat();
        seat.setColumn(1);
        seat.setRow(2);
        seat.setHallId(2);
        assertTrue(seatMapper.insertSelective(seat)!=0);
    }

    @Test
    @Transactional
    public void selectByIDTest(){
        assertNotNull(seatMapper.selectByPrimaryKey(1));
    }

    @Test
    @Transactional
    public void updateSelectiveTestBySeat(){
        Seat seat = new Seat(31,2,2,2);
        assertTrue(seatMapper.updateByPrimaryKeySelective(seat)!=0);
    }

    @Test
    @Transactional
    public void selectByHallIDTest(){
        assertNotNull(seatMapper.selectByHallID(1));
    }

    @Test
    @Transactional
    public void deleteByHallIDTest(){
        assertTrue(seatMapper.deleteByHallID(2)!=0);
    }
}
