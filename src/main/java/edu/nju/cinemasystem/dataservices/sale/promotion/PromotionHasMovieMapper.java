package edu.nju.cinemasystem.dataservices.sale.promotion;

import edu.nju.cinemasystem.data.po.PromotionHasMovie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PromotionHasMovieMapper {
    int deleteByPrimaryKey(PromotionHasMovie key);

    int insert(PromotionHasMovie record);

    int insertSelective(PromotionHasMovie record);

    /**
     * 通过优惠活动id来查找优惠活动包含的所有电影
     * @param promotionID 优惠活动id
     * @return 该活动包含的所有电影ID
     */
    List<PromotionHasMovie> selectByPromotionID(int promotionID);

    /**
     * 通过电影id来查找它参与的所有活动
     * @param movieID 电影id
     * @return 电影参与的所有优惠活动
     */
    List<PromotionHasMovie> selectByMovieID(@Param("movieID") Integer movieID);
}