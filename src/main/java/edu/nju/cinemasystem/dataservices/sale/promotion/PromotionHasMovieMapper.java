package edu.nju.cinemasystem.dataservices.sale.promotion;

import edu.nju.cinemasystem.data.po.PromotionHasMovie;

public interface PromotionHasMovieMapper {
    int deleteByPrimaryKey(PromotionHasMovie key);

    int insert(PromotionHasMovie record);

    int insertSelective(PromotionHasMovie record);
}