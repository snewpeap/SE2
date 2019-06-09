package edu.nju.cinemasystem.dataservices.sale.ticket;

import edu.nju.cinemasystem.data.po.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface TicketsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    Ticket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    /**
     * 通过userID获取用户的所有票
     * @param userID 用户id
     * @return 票列表
     */
    List<Ticket> selectByUserID(int userID);

    /**
     * 通过订单id获取该订单的所有票
     * @param orderID 订单id
     * @return 订单的票列表
     */
    List<Ticket> selectByOrderID(long orderID);

    /**
     * 通过电影id获取该电影的所有票，不只是已生效的
     * @param movieID 电影id
     * @return 电影的票列表
     */
    List<Ticket> selectByMovieID(int movieID);

    /**
     * 通过排片id获得排片的票（可能是所有状态的）
     * @param arrangementID 排片id
     * @return 票
     */
    List<Ticket> selectByArrangementID(int arrangementID);

    /**
     * 查询在这两个日期内（晚于startDate的0时，早于endDate的0时）的票
     * @param startDate 起始时间
     * @param endDate 结束时间
     * @return 匹配的票
     */
    @Deprecated
    List<Ticket> selectByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}