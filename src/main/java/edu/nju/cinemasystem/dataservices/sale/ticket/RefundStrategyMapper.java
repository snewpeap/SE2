package edu.nju.cinemasystem.dataservices.sale.ticket;

import edu.nju.cinemasystem.data.po.RefundStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RefundStrategyMapper {
    int deleteByPrimaryKey(Integer day);

    int insert(RefundStrategy record);

    int insertSelective(RefundStrategy record);

    RefundStrategy selectByPrimaryKey(Integer day);

    int updateByPrimaryKeySelective(RefundStrategy record);

    int updateByPrimaryKey(RefundStrategy record);
}