package edu.nju.cinemasystem.dataservices.sale.promotion;

import edu.nju.cinemasystem.data.po.PromotionHasMovie;

import java.util.List;

public interface PromotionHasMovieMapper {
    int deleteByPrimaryKey(PromotionHasMovie key);

    int insert(PromotionHasMovie record);

    int insertSelective(PromotionHasMovie record);

    //TODO
    List<PromotionHasMovie> selectByPromotionID(int PromotionID);

    //TODO
    List<PromotionHasMovie> selectByMovieID(int movieID);
}