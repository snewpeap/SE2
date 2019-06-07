package edu.nju.cinemasystem.dataservices.cinema.hall;

import edu.nju.cinemasystem.data.po.Seat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SeatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Seat record);

    int insertSelective(Seat record);

    Seat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Seat record);

    int updateByPrimaryKey(Seat record);

    //TODO redundant
    List<Seat> selectByHallID(int hallID);
}