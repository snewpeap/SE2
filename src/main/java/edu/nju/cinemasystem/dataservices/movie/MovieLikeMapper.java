package edu.nju.cinemasystem.dataservices.movie;

import edu.nju.cinemasystem.data.po.MovieLike;

public interface MovieLikeMapper {
    int insert(MovieLike record);

    int insertSelective(MovieLike record);
}