package edu.nju.cinemasystem.dataservices.vip;

import edu.nju.cinemasystem.data.po.Vipcard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VipcardMapper {
    /**
     * 通过userID也即会员卡id删除会员卡，会用到吗？
     * @param userId 用户id
     * @return 操作的行数
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 新增一张会员卡，余额不需要填，默认初始值为0
     * @param record VipcardPO
     * @return 操作的行数
     */
    int insert(Vipcard record);

    /**
     * 同insert
     * @param record VipcardPO
     * @return 操作的行数
     */
    int insertSelective(Vipcard record);

    /**
     * 通过userID也即会员卡id来查询会员卡
     * @param userId 用户id
     * @return 匹配的会员卡
     */
    Vipcard selectByPrimaryKey(Integer userId);

    /**
     * 通过id唯一更新一条会员卡记录
     * @param record VipcardPO
     * @return 操作的行数
     */
    int updateByPrimaryKeySelective(Vipcard record);

    /**
     * 通过id唯一更新一条会员卡记录
     * @param record VipcardPO
     * @return 操作的行数
     */
    int updateByPrimaryKey(Vipcard record);

    /**
     * 查询所有VIP
     * @return 所有VIP
     */
    List<Vipcard> selectAll();
}