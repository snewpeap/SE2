package edu.nju.cinemasystem.dataservices.vip;

import edu.nju.cinemasystem.data.po.Vipcard;

public interface VipcardMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Vipcard record);

    int insertSelective(Vipcard record);

    Vipcard selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Vipcard record);

    int updateByPrimaryKey(Vipcard record);
}