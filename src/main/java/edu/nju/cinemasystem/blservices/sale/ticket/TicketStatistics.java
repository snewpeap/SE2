package edu.nju.cinemasystem.blservices.sale.ticket;

import java.util.Date;

public interface TicketStatistics {
    /**
     * 获取某天用户的客单价
     * @param startDate
     * @param endDate
     * @return
     */
    float getAudiencePriceByDay(Date startDate, Date endDate);
}
