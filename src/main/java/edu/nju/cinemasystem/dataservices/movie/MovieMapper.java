package edu.nju.cinemasystem.dataservices.movie;

import edu.nju.cinemasystem.data.po.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MovieMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Movie record);

    int insertSelective(Movie record);

    Movie selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Movie record);

    int updateByPrimaryKeyWithBLOBs(Movie record);

    int updateByPrimaryKey(Movie record);
}