package edu.nju.cinemasystem.dataservices.cinema.arrangement;

import edu.nju.cinemasystem.data.po.ArrangementSeat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArrangementSeatMapper {
    int insert(ArrangementSeat record);

    int insertSelective(ArrangementSeat record);

    //TODO
    int updateSeatStatus(ArrangementSeat record);

    //TODO
    int delete(ArrangementSeat record);

    //TODO
    List<ArrangementSeat> selectByArrangementID(int ArrangementID);

    //TODO
    ArrangementSeat select(ArrangementSeat record);
}