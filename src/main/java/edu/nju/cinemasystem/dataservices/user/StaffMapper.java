package edu.nju.cinemasystem.dataservices.user;

import edu.nju.cinemasystem.data.po.Staff;
import edu.nju.cinemasystem.data.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StaffMapper extends UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Staff record);

    int insertSelective(Staff record);

    List<User> selectAll();

    User selectByName(String name);
}