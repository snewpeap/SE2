package edu.nju.cinemasystem.dataservices.cinema.arrangement;

import edu.nju.cinemasystem.data.po.ArrangementSeat;

public interface ArrangementSeatMapper {
    int insert(ArrangementSeat record);

    int insertSelective(ArrangementSeat record);
}