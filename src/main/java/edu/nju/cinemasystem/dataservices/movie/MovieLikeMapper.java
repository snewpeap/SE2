package edu.nju.cinemasystem.dataservices.movie;

import edu.nju.cinemasystem.data.po.MovieLike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MovieLikeMapper {
    /**
     * 插入一条想看电影纪录，时间请务必填入
     * @param record MovieLikePO
     * @return 操作的行数
     */
    int insert(MovieLike record);

    /**
     * 插入一条想看电影纪录，为null的值不会被插入
     * 这时若时间为空，数据库会填入现在的时间，慎用
     * @param record MovieLikePO
     * @return 操作的行数
     */
    int insertSelective(MovieLike record);

    /**
     * 通过用户id和电影id唯一查询一个想看记录
     * @param record MovieLikePO，两个id必填
     * @return 匹配的想看记录
     */
    MovieLike selectByUserAndMovie(MovieLike record);

    /**
     * 通过电影ID查询该电影的想看记录
     * @param movieID 电影ID
     * @return 电影的想看记录
     */
    List<MovieLike> selectByMovieID(int movieID);

    List<Map<String, Object>> selectByMovieGroupByDate(int movieID);

    /**
     * 通过用户id查询用户的想看记录
     * @param userID 用户id
     * @return 用户的想看记录
     */
    List<MovieLike> selectByUserID(int userID);

    /**
     * 通过电影id和用户id删除一条想看记录
     * @param record MovieLikePO，两个id必填
     * @return 操作的行数
     */
    int deleteByUserAndMovie(MovieLike record);
}