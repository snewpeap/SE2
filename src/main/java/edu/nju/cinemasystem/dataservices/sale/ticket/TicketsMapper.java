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

    //TODO
    List<Ticket> selectByUserID(int userID);

    //TODO
    List<Ticket> selectByOrderID(long orderID);

    //TODO
    List<Ticket> selectBymovieID(int movieID);

    //TODO 返回这两个日期内的票
    List<Ticket> selectByDate(Date startDate, Date endDate);

    //TODO
    List<Ticket> selectByArrangementID(int arrangementID);
}