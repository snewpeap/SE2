package edu.nju.cinemasystem.dataservices.vip;

import edu.nju.cinemasystem.data.po.Vipcard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VipcardMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Vipcard record);

    int insertSelective(Vipcard record);

    Vipcard selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Vipcard record);

    int updateByPrimaryKey(Vipcard record);
}