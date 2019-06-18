package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementManage;
import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementService;
import edu.nju.cinemasystem.blservices.sale.ticket.TicketStatistics;
import edu.nju.cinemasystem.data.po.Arrangement;
import edu.nju.cinemasystem.data.po.Ticket;
import edu.nju.cinemasystem.dataservices.sale.ticket.TicketsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketStatisticsImpl implements TicketStatistics {

    private final TicketsMapper ticketsMapper;
    private final ArrangementService arrangementService;
    private final ArrangementManage arrangementManage;

    @Autowired
    public TicketStatisticsImpl(TicketsMapper ticketsMapper, ArrangementService arrangementService, ArrangementManage arrangementManage) {
        this.ticketsMapper = ticketsMapper;
        this.arrangementService = arrangementService;
        this.arrangementManage = arrangementManage;
    }

    @Override
    public float getAudiencePriceByDay(Date startDate, Date endDate) {
        List<Arrangement> arrangements = arrangementManage.getArrangementsByDay(startDate, endDate);
        List<Ticket> tickets = new ArrayList<>();
        arrangements.forEach(arrangement1 -> tickets.addAll(ticketsMapper.selectByArrangementID(arrangement1.getId())));
        List<Ticket> effectiveTickets = new ArrayList<>();
        float totalAmount = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() == (byte) 1) {
                effectiveTickets.add(ticket);
                totalAmount += ticket.getRealAmount();
            }
        }
        float ef = (float) effectiveTickets.size();
        if(ef == 0){
            ef = 1;
        }
        return totalAmount / ef;
    }

    @Override
    public int getNumOfTicketsByArrangement(int arrangementID) {
        List<Ticket> tickets = ticketsMapper.selectByArrangementID(arrangementID);
        int num = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() == (byte) 1) {
                num++;
            }
        }
        return num;
    }

    @Override
    public float getBoxOfficeByMovieIDAndDay(int movieID, Date startDate, Date endDate) {
        List<Arrangement> arrangements = arrangementManage.getArrangementsByDay(startDate, endDate);
        List<Ticket> tickets = new ArrayList<>();
        arrangements.forEach(arrangement1 -> tickets.addAll(ticketsMapper.selectByArrangementID(arrangement1.getId())));
        float totalBoxOffice = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getStatus() == (byte) 1 && arrangementService.getMovieIDbyID(ticket.getArrangementId()) == movieID) {
                totalBoxOffice += arrangementService.getFareByID(ticket.getArrangementId());
            }
        }
        return totalBoxOffice;
    }

    @Override
    public float getTotalBoxOfficeByMovieID(int movieID) {
        List<Ticket> tickets = ticketsMapper.selectByMovieID(movieID);
        float totalBoxOffice = 0;
        for (Ticket ticket : tickets) {
            totalBoxOffice += arrangementService.getFareByID(ticket.getArrangementId());
        }
        return totalBoxOffice;
    }
}
