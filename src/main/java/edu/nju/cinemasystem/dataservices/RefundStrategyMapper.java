package edu.nju.cinemasystem.dataservices;

import edu.nju.cinemasystem.data.po.RefundStrategy;

public interface RefundStrategyMapper {
    int deleteByPrimaryKey(Integer day);

    int insert(RefundStrategy record);

    int insertSelective(RefundStrategy record);

    RefundStrategy selectByPrimaryKey(Integer day);

    int updateByPrimaryKeySelective(RefundStrategy record);

    int updateByPrimaryKey(RefundStrategy record);
}