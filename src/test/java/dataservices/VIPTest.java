package dataservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.data.po.Vipcard;
import edu.nju.cinemasystem.dataservices.vip.VipcardMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class VIPTest {

    @Autowired
    private VipcardMapper vipcardMapper;

    @Test
    public void testSelectAll(){
        List<Vipcard> vipcards = vipcardMapper.selectAll();
        assertNotNull(vipcards);
    }
}
