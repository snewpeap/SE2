package edu.nju.cinemasystem.dataservices;

import edu.nju.cinemasystem.data.po.Promotion;

public interface PromotionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Promotion record);

    int insertSelective(Promotion record);

    Promotion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Promotion record);

    int updateByPrimaryKeyWithBLOBs(Promotion record);

    int updateByPrimaryKey(Promotion record);
}