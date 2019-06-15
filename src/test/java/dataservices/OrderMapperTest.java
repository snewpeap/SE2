package dataservices;


import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.dataservices.sale.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderMapperTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    @Transactional
    public void testSelectByUserIDAndOrderID(){
        int i = orderMapper.selectByUserAndOrderID(1,156061226072135L).size();
        assertTrue(i!=0);
    }
}
