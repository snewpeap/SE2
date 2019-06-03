package edu.nju.cinemasystem.dataservices;

import edu.nju.cinemasystem.data.po.Arrangement;

public interface ArrangementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Arrangement record);

    int insertSelective(Arrangement record);

    Arrangement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Arrangement record);

    int updateByPrimaryKey(Arrangement record);
}