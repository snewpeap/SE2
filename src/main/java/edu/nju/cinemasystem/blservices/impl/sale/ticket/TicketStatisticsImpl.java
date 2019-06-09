package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import edu.nju.cinemasystem.blservices.cinema.arrangement.Arrangement;
import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
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
    @Autowired
    Arrangement arrangement;
    @Autowired
    ArrangementManage arrangementManage;

    @Override
    public float getAudiencePriceByDay(Date startDate, Date endDate) {
        List<edu.nju.cinemasystem.data.po.Arrangement> arrangements = arrangementManage.getArrangementsByDay(startDate,endDate);
        List<Ticket> tickets = new ArrayList<>();
        arrangements.forEach(arrangement1 -> {
            tickets.addAll(ticketsMapper.selectByArrangementID(arrangement1.getId()));
        });
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

    @Override
    public int getNumOfTicketsByArrangement(int arrangementID) {
        List<Ticket> tickets = ticketsMapper.selectByArrangementID(arrangementID);
        int num = 0;
        for(Ticket ticket:tickets){
            if(ticket.getStatus()==(byte)1){
                num++;
            }
        }
        return num;
    }

    @Override
    public float getBoxOfficeByMovieIDAndDay(int movieID, Date startDate, Date endDate) {
        List<Ticket> tickets = ticketsMapper.selectByDate(startDate, endDate);
        float totalBoxOffice = 0;
        for(Ticket ticket:tickets){
            if(ticket.getStatus()==(byte)1 && arrangement.getMovieIDbyID(ticket.getArrangementId()) == movieID){
               totalBoxOffice += arrangement.getFareByID(ticket.getArrangementId());
            }
        }
        return totalBoxOffice;
    }
}
