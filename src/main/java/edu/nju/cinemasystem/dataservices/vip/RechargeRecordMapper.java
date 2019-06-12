package edu.nju.cinemasystem.dataservices.vip;

import edu.nju.cinemasystem.data.po.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    List<RechargeRecord> selectByUserID(Integer userID);
}