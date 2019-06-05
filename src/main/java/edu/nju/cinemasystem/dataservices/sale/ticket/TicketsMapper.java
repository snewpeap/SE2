package edu.nju.cinemasystem.dataservices.sale.ticket;

import edu.nju.cinemasystem.data.po.Tickets;

public interface TicketsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tickets record);

    int insertSelective(Tickets record);

    Tickets selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tickets record);

    int updateByPrimaryKey(Tickets record);
}