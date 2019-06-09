package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import edu.nju.cinemasystem.blservices.sale.ticket.TicketStatistics;

import edu.nju.cinemasystem.data.po.Ticket;
import edu.nju.cinemasystem.dataservices.sale.ticket.TicketsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketStatisticsImpl implements TicketStatistics {

    @Autowired
    TicketsMapper ticketsMapper;

    @Override
    public float getAudiencePriceByDay(Date startDate, Date endDate) {
        List<Ticket> tickets = ticketsMapper.selectByDate(startDate, endDate);
        List<Ticket> effectiveTickets = new ArrayList<>();
        float totalAmount = 0;
        for(Ticket ticket:tickets){
            if(ticket.getStatus()==(byte)1){
                effectiveTickets.add(ticket);
                totalAmount += ticket.getRealAmount();
            }
        }
        return totalAmount/(float)effectiveTickets.size();
    }
}
