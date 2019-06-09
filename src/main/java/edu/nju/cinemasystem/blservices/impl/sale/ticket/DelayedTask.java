package edu.nju.cinemasystem.blservices.impl.sale.ticket;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import edu.nju.cinemasystem.data.po.Ticket;

public class DelayedTask implements Delayed {

    private long ID;
    private Date startTime;
    private List<Ticket> tickets;

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long DELAY = 15 * 60 * 1000L;
        return unit.convert(this.startTime.getTime() + DELAY - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public long getID() {
        return ID;
    }

    public void setID(long iD) {
        ID = iD;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    DelayedTask(long iD, Date startTime, List<Ticket> tickets) {
        ID = iD;
        this.startTime = startTime;
        this.tickets = tickets;
    }

}