package edu.nju.cinemasystem.dataservices.user;

import edu.nju.cinemasystem.data.po.UserHasRoleKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserHasRoleMapper {
    int deleteByUserID(int userID);

    int updateByUserID(UserHasRoleKey record);

    int insert(UserHasRoleKey record);

    int insertSelective(UserHasRoleKey record);

    UserHasRoleKey selectByUserID(int userID);
}