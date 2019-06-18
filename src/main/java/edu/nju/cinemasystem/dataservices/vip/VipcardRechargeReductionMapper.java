package edu.nju.cinemasystem.dataservices.vip;

import edu.nju.cinemasystem.data.po.VipcardRechargeReduction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface VipcardRechargeReductionMapper {
    /**
     * 通过起点金额来删除一个充值满减策略
     * @param targetAmount 起点金额
     * @return 操作的行数
     */
    int deleteByPrimaryKey(@Param("targetAmount") Integer targetAmount);

    /**
     * 新增一个充值满减策略
     * @param record 策略PO
     * @return 操作的条数
     */
    int insert(VipcardRechargeReduction record);

    /**
     * 同insert
     * @param record 策略PO
     * @return 操作的条数
     */
    int insertSelective(VipcardRechargeReduction record);

    /**
     * 通过起点金额来查询一个满减策略，当targetAmount为null的时候查询所有
     * @param targetAmount 起点金额
     * @return 满减策略
     */
    List<VipcardRechargeReduction> select(@Param("targetAmount") Integer targetAmount);

    /**
     * 通过起点金额来唯一更新一条满减策略
     * @param record 满减策略
     * @return 操作的条数
     */
    int updateByPrimaryKeySelective(VipcardRechargeReduction record);

    /**
     * 通过起点金额来唯一更新一条满减策略
     * @param record 满减策略
     * @return 操作的条数
     */
    int updateByPrimaryKey(VipcardRechargeReduction record);

    /**
     * 通过金额来寻找最适合的充值策略
     * @param amount 金额
     * @return 选中的充值策略
     */
    VipcardRechargeReduction selectByAmount(@Param("amount") Float amount);
}