package edu.nju.cinemasystem.dataservices.cinema.hall;

import edu.nju.cinemasystem.data.po.Hall;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HallMapper {
    /**
     * 给定一个影厅id，删除这个影厅，慎用，用之前请考虑其他表的引用
     * @param id 影厅id
     * @return 操作的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入一个影厅,将参数的id属性值设为插入的id
     * @param record HallPO
     * @return 操作的行数，id在record对象里了
     */
    int insert(Hall record);

    /**
     * 插入一个影厅，为null的值不会被添加，参数的id设为插入后的id
     * @param record HallPO
     * @return 操作的行数，id在record对象里了
     */
    int insertSelective(Hall record);

    /**
     * 通过id查找影厅
     * @param id 影厅id
     * @return 影厅对象
     */
    Hall selectByPrimaryKey(Integer id);

    /**
     * 通过影厅id唯一更新一个影厅纪录，为null的值不会被添加，必须有id
     * @param record HallPO
     * @return 操作的行数
     */
    int updateByPrimaryKeySelective(Hall record);

    /**
     * 通过影厅id唯一更新一个影厅纪录，必须有id
     * @param record HallPO
     * @return 操作的行数
     */
    int updateByPrimaryKey(Hall record);

    /**
     * 查询所有的影厅
     * @return 所有影厅PO
     */
    List<Hall> selectAll();
}