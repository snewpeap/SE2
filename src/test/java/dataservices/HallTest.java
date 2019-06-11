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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HallTest {

    @Autowired
    HallMapper hallMapper;

    @Test
    @Transactional
    public void insertSelectiveTest() {
        Hall hall = new Hall();
        hall.setName("啊啊啊啊");
        hall.setColumn(30);
        hall.setRow(10);
        hall.setSize((byte) 0);
        hall.setIs3d((byte) 1);
        hall.setIsImax((byte) 1);
        int i = hallMapper.insertSelective(hall);
        assert i == 1;
    }

    @Test
    @Transactional
    public void insertTest() {
        Hall hall = new Hall();
        hall.setName("啊啊啊啊");
        hall.setColumn(30);
        hall.setRow(10);
        hall.setSize((byte) 0);
        hall.setIs3d((byte) 1);
        hall.setIsImax((byte) 1);
        int i = hallMapper.insert(hall);

        assert 1 == hall.getId();
    }
}
