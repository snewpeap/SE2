package edu.nju.cinemasystem.dataservices.cinema.arrangement;

import edu.nju.cinemasystem.data.po.ArrangementSeat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArrangementSeatMapper {
    /**
     * 插入一条记录
     * @param record ArrangementSeatPO
     * @return 调用者应拥有id，所以返回操作的行数
     */
    int insert(ArrangementSeat record);

    /**
     * 插入一条记录，为null的属性不会被写进sql语句中
     * @param record ArrangementSeatPO
     * @return 调用者应拥有id，所以返回操作的行数
     */
    int insertSelective(ArrangementSeat record);

    /**
     * 更新一条记录，两个id都必须有
     * @param record ArrangementSeatPO
     * @return 调用者应拥有id，所以返回操作的行数
     */
    int updateSeatStatus(ArrangementSeat record);

    /**
     * 虽然说用途是删除一条记录
     * @param record 包含seatId和arrangementId的PO
     * @return 操作的记录条数，0为没有进行任何操作
     */
    int delete(ArrangementSeat record);

    /**
     * 通过排片ID获取记录
     * @param arrangementId 排片id
     * @return 此排片id对应的所有记录
     */
    List<ArrangementSeat> selectByArrangementID(int arrangementId);

    /**
     * 查找一条记录
     * @param record ArrangementSeatPO
     * @return 找到的PO
     */
    ArrangementSeat select(ArrangementSeat record);
}