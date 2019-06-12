package edu.nju.cinemasystem.dataservices.movie;

import edu.nju.cinemasystem.data.po.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MovieMapper {
    /**
     * 通过id删除一部电影记录
     * @param id 电影id
     * @return 操作的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增一部电影，id放在参数对象里了
     * @param record MoviePO
     * @return 操作的行数
     */
    int insert(Movie record);

    /**
     * 新增一部电影，为null的属性不会被添加
     * id放在参数对象里了
     * @param record MoviePO
     * @return 操作的行数
     */
    int insertSelective(Movie record);

    /**
     * 通过id查询电影
     * @param id 电影id
     * @return 电影PO
     */
    Movie selectByPrimaryKey(Integer id);

    /**
     * 通过id唯一更新一部电影，更新description
     * @param record MoviePO
     * @return 操作的行数
     */
    int updateByPrimaryKeySelective(Movie record);

    /**
     * 查询所有电影
     * @return 所有电影
     */
    List<Movie> selectAll();
}