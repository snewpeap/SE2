package blservices;

import edu.nju.cinemasystem.Application;
import edu.nju.cinemasystem.blservices.sale.ticket.TicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Test
    public void test(){
        ticketService.getHistoricalConsumptionsByUserId(1);
    }
}
