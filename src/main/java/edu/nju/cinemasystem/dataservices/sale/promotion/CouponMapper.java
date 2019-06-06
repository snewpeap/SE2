package edu.nju.cinemasystem.dataservices.sale.promotion;

import edu.nju.cinemasystem.data.po.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    //TODO
    List<Coupon> selectByUserID(int userID);
}