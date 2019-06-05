package edu.nju.cinemasystem.dataservices.sale.ticket;

import edu.nju.cinemasystem.data.po.Ticket;

import java.util.List;

public interface TicketsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    Ticket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    //TODO
    List<Ticket> selectByUserID(int userID);

    //TODO
    List<Ticket> selectByOrderID(long orderID);

    //TODO
    List<Ticket> selectBymovieID(int movieID);
}