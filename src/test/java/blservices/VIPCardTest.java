package blservices;


import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.vip.VIPCardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class VIPCardTest {

    @Autowired
    VIPCardService vipCardService;

    @Test
    @Transactional
    public void testDeposit(){
        assertTrue(vipCardService.deposit(5,50).isSuccess());
    }
}
