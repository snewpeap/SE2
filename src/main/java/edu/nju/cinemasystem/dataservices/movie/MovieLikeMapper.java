package edu.nju.cinemasystem.dataservices.movie;

import edu.nju.cinemasystem.data.po.MovieLike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MovieLikeMapper {
    int insert(MovieLike record);

    int insertSelective(MovieLike record);

    MovieLike selectByUserAndMovie(MovieLike record);

    List<MovieLike> selectByMovieID(int movieID);

    List<MovieLike> selectByUserID(int userID);

    int deleteByUserAndMovie(MovieLike record);
}