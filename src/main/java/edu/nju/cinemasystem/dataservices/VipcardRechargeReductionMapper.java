package edu.nju.cinemasystem.dataservices;

import edu.nju.cinemasystem.data.po.VipcardRechargeReduction;

public interface VipcardRechargeReductionMapper {
    int deleteByPrimaryKey(Integer targetAmount);

    int insert(VipcardRechargeReduction record);

    int insertSelective(VipcardRechargeReduction record);

    VipcardRechargeReduction selectByPrimaryKey(Integer targetAmount);

    int updateByPrimaryKeySelective(VipcardRechargeReduction record);

    int updateByPrimaryKey(VipcardRechargeReduction record);
}