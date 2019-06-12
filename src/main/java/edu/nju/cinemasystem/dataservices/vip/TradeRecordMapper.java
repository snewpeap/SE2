package edu.nju.cinemasystem.dataservices.vip;

import edu.nju.cinemasystem.data.po.TradeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TradeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeRecord record);

    int insertSelective(TradeRecord record);

    TradeRecord selectByPrimaryKey(Integer id);

    List<TradeRecord> selectByUserID(Integer userID);
}