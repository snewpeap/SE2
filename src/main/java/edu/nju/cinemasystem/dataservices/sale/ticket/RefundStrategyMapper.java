package edu.nju.cinemasystem.dataservices.sale.ticket;

import edu.nju.cinemasystem.data.po.RefundStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RefundStrategyMapper {
    int deleteByPrimaryKey(Integer day);

    int insert(RefundStrategy record);

    int insertSelective(RefundStrategy record);

    RefundStrategy selectByPrimaryKey(Integer day);

    int updateByPrimaryKeySelective(RefundStrategy record);

    int updateByPrimaryKey(RefundStrategy record);

    /**
     * 获取所有退票策略
     *
     * @return 所有退票策略列表
     */
    List<RefundStrategy> selectAll();

    RefundStrategy selectByDay(long dayToCome);
}