package edu.nju.cinemasystem.blservices.sale.ticket;

import java.util.Date;

public interface TicketStatistics {
    /**
     * 获取某天用户的客单价
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 客单价
     */
    float getAudiencePriceByDay(Date startDate, Date endDate);

    /**
     * 获取某场排片的票的数量
     * @param arrangementID 排片ID
     * @return 票的数量
     */
    int getNumOfTicketsByArrangement(int arrangementID);

    /**
     * 获取某段时间内电影的票房
     * @param movieID 电影ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 票房
     */
    float getBoxOfficeByMovieIDAndDay(int movieID, Date startDate, Date endDate);

    /**
     * 返回某部电影总票房
     * @param movieID 电影ID
     * @return 票房
     */
    float getTotalBoxOfficeByMovieID(int movieID);
}
