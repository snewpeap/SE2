package edu.nju.cinemasystem.dataservices.cinema.arrangement;

import edu.nju.cinemasystem.data.po.Arrangement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ArrangementMapper {
    /**
     * 根据id来删除排片
     *
     * @param id 排片ID
     * @return 操作的行数，0为没有进行操作，考虑参数错误
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入排片记录
     *
     * @param record ArrangementPO
     * @return 操作的行数，0为没有进行操作，考虑参数错误，id请对插入的PO使用getID
     */
    int insert(Arrangement record);

    /**
     * 插入排片记录，为null的属性不会被写进sql语句中
     *
     * @param record ArrangementPO
     * @return 操作的行数，0为没有进行操作，考虑参数错误，id请对插入的PO使用getID
     */
    int insertSelective(Arrangement record);

    /**
     * 通过排片ID查找排片记录
     *
     * @param id 排片ID
     * @return 操作的行数，0为没有进行操作，考虑参数错误
     */
    Arrangement selectByPrimaryKey(Integer id);

    /**
     * 更新排片记录，为null的属性不会被写进sql语句中
     *
     * @param record ArrangementPO
     * @return 操作的行数，0为没有进行操作，考虑参数错误
     */
    int updateByPrimaryKeySelective(Arrangement record);

    /**
     * 更新排片记录，只会根据主键（id）更新唯一的一条记录
     *
     * @param record ArrangementPO,id不能为空
     * @return 操作的行数，0为没有进行操作，考虑参数错误
     */
    int updateByPrimaryKey(Arrangement record);

    /**
     * 通过电影ID查找排片
     *
     * @param movieID 电影ID
     * @return 当前时刻之后及在可见时间之后的该电影所有排片
     */
    List<Arrangement> selectByMovieID(int movieID);

    /**
     * 通过影厅ID和一个开始日期查找排片，默认返回duration天内的排片
     *
     * @param hallID    影厅id
     * @param startDate 开始的时间，结束时间晚于开始时间当天0点的不会被返回
     * @param duration  天数，使用应用配置请
     * @return 符合条件的所有排片
     */
    List<Arrangement> selectByHallIDAndStartDate(
            @Param("hallID") int hallID,
            @Param("startDate") Date startDate,
            @Param("duration") int duration
    );

    /**
     * 查找当前时间后的在该影厅的所有排片，没有返回null
     *
     * @param hallID      影厅id
     * @param currentTime 开始的时间，结束时间晚于开始时间当天0点的不会被返回
     * @return 符合条件的所有排片，没有返回null
     */
    List<Arrangement> selectByHallIDAndCurrentTime(
            @Param("hallID") int hallID,
            @Param("currentTime") Date currentTime
    );

    /**
     * 获取开始时间晚于startDay的0时，结束时间早于endDay的0时的所有排片
     *
     * @param startDay 起始时间
     * @param endDay   结束时间
     * @return 匹配的排片
     */
    List<Arrangement> selectByDay(@Param("startDay") Date startDay, @Param("endDay") Date endDay);

    List<Arrangement> selectTimeConflict(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}