package edu.nju.cinemasystem.dataservices.sale.ticket;

import edu.nju.cinemasystem.data.po.Ticket;
import org.apache.ibatis.annotations.Mapper;
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
    List<Ticket> selectBymovieID(int movieID);

    //TODO 返回这两个日期内的票
    List<Ticket> selectByDate(Date startDate, Date endDate);
}