package dataservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.data.po.Hall;
import edu.nju.cinemasystem.dataservices.cinema.hall.HallMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HallTest {

    @Autowired
    HallMapper hallMapper;

    @Test
    @Transactional
    public void insertSelectiveTest() {
        Hall hall = new Hall();
        hall.setName("Selective");
        hall.setColumn(20);
        hall.setRow(10);
        hall.setSize((byte) 0);
        hall.setIs3d((byte) 1);
        hall.setIsImax((byte) 1);
        hallMapper.insertSelective(hall);
        assertTrue(hall.getId()!=0);
    }

    @Test
    @Transactional
    public void insertTest() {
        Hall hall = new Hall();
        hall.setName("insertTest");
        hall.setColumn(15);
        hall.setRow(8);
        hall.setSize((byte) 2);
        hall.setIs3d((byte) 0);
        hall.setIsImax((byte) 1);
        int i = hallMapper.insert(hall);
        assertTrue(i!=0);
    }

    @Test(expected = Exception.class)
    @Transactional
    public void insertSelectiveTestNull() {
        Hall hall = new Hall();
        hall.setName("TestNull");
        hall.setColumn(15);
        hall.setRow(8);
        hall.setSize((byte) 2);
        hall.setIs3d(null);
        hall.setIsImax(null);
        int i = hallMapper.insertSelective(hall);
        assertEquals(0, i);
    }
}
