package edu.nju.cinemasystem.dataservices.sale.promotion;

import edu.nju.cinemasystem.data.po.Promotion;

import java.util.List;

public interface PromotionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Promotion record);

    int insertSelective(Promotion record);

    Promotion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Promotion record);

    int updateByPrimaryKeyWithBLOBs(Promotion record);

    int updateByPrimaryKey(Promotion record);

    //TODO
    List<Promotion> selectAll();
}