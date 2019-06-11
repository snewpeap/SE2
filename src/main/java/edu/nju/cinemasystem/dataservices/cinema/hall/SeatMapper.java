package edu.nju.cinemasystem.dataservices.cinema.hall;

import edu.nju.cinemasystem.data.po.Seat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SeatMapper {
    /**
     * 通过座位id删除座位纪录，但是会用得到吗
     * @param id 座位id
     * @return 操作的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入一个座位纪录，插入成功后id被填充在record的id里
     * @param record SeatPO
     * @return 操作的行数
     */
    int insert(Seat record);

    /**
     * 插入一个座位纪录，为null的属性不会被添加。
     * 插入成功后id被填充在record的id里
     * @param record SeatPO
     * @return 操作的行数
     */
    int insertSelective(Seat record);

    /**
     * 通过座位id获取座位纪录
     * @param id 座位id
     * @return 座位PO
     */
    Seat selectByPrimaryKey(Integer id);

    /**
     * 通过id唯一更新一条座位纪录，为null的属性不会被添加
     * id万万不可为空
     * @param record SeatPo
     * @return 操作的行数
     */
    int updateByPrimaryKeySelective(Seat record);

    /**
     * 通过id唯一更新一条座位纪录
     * id不可为空
     * @param record SeatPO
     * @return 操作的行数
     */
    int updateByPrimaryKey(Seat record);

    /**
     * 通过影厅id查找到影厅的所有座位
     * @param hallID 影厅id
     * @return 影厅的所有座位
     */
    List<Seat> selectByHallID(int hallID);

    /**
     * 返回所有座位
     * @return 所有座位
     */
    List<Seat> selectAll();

    /**
     * 根据影厅id删除影厅的所有座位
     * @param hallID 影厅id
     * @return 操作的行数(座位数)
     */
    int deleteByHallID(int hallID);
}