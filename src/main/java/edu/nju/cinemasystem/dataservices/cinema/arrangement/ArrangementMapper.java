package edu.nju.cinemasystem.dataservices.cinema.arrangement;

import edu.nju.cinemasystem.data.po.Arrangement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ArrangementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Arrangement record);

    int insertSelective(Arrangement record);

    Arrangement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Arrangement record);

    int updateByPrimaryKey(Arrangement record);

    //TODO
    List<Arrangement> selectByMovieID(int movieID);

    //TODO
    List<Arrangement> selectByHallID(int hallID);

    //TODO
    List<Arrangement> selectByStartDate(Date startDate);
}