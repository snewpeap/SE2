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

    /**
     * 获取某场排片的票的数量
     * @param arrangementID
     * @return
     */
    int getNumOfTicketsByArrangement(int arrangementID);

    /**
     * 获取某段时间内电影的票房
     * @param movieID
     * @param startDate
     * @param endDate
     * @return
     */
    float getBoxOfficeByMovieIDAndDay(int movieID, Date startDate, Date endDate);

    /**
     * 返回某部电影总票房
     * @param movieID
     * @return
     */
    float getTotalBoxOfficeByMovieID(int movieID);
}
