package edu.nju.cinemasystem.dataservices.cinema.hall;

import edu.nju.cinemasystem.data.po.Seat;

public interface SeatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Seat record);

    int insertSelective(Seat record);

    Seat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Seat record);

    int updateByPrimaryKey(Seat record);
}