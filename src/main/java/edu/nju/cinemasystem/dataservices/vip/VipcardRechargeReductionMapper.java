package edu.nju.cinemasystem.dataservices.vip;

import edu.nju.cinemasystem.data.po.VipcardRechargeReduction;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VipcardRechargeReductionMapper {
    int deleteByPrimaryKey(Integer targetAmount);

    int insert(VipcardRechargeReduction record);

    int insertSelective(VipcardRechargeReduction record);

    VipcardRechargeReduction selectByPrimaryKey(Integer targetAmount);

    int updateByPrimaryKeySelective(VipcardRechargeReduction record);

    int updateByPrimaryKey(VipcardRechargeReduction record);
}