package edu.nju.cinemasystem.dataservices.cinema.hall;

import edu.nju.cinemasystem.data.po.Hall;

public interface HallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hall record);

    int insertSelective(Hall record);

    Hall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hall record);

    int updateByPrimaryKey(Hall record);
}